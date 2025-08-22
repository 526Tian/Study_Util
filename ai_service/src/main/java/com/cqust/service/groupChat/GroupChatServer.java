package com.cqust.service.groupChat;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Ltian
 * @date 2025/8/22 22:32
 * @description:
 */
@Slf4j
public class GroupChatServer {

    private ServerSocketChannel serverSocketChannel;

    private Selector selector;

    private static final String HOST = "127.0.0.1";

    private static final Integer PORT = 7000;

    public GroupChatServer() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(HOST, PORT));
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            log.info("服务端准备就绪...");
        } catch (Exception e) {
            log.error("GroupChatServer初始化异常", e);
        }
    }

    public void listen() {
        while(true) {
            try {
                if (selector.select(2000) == 0) {
                    continue;
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while(iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        log.info("{} 客户端已上线", socketChannel.getRemoteAddress());
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        iterator.remove();
                    }
                    if (selectionKey.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                        forwardInfo(getBufferInfo(selectionKey, socketChannel), socketChannel);
                        iterator.remove();
                    }
                }
            } catch (Exception e) {
                log.error("服务端接受消息异常", e);
            }
        }
    }

    private String getBufferInfo(SelectionKey selectionKey, SocketChannel socketChannel) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ByteBuffer buffer = ByteBuffer.allocate(16);
        try {
            while (socketChannel.read(buffer) > 0) {
                buffer.flip();
                byte[] data = new byte[buffer.remaining()];
                buffer.get(data);
                baos.write(data);
                buffer.clear();
            }
        }catch (Exception e) {
            try {
                log.error("{} 客户端已下线", socketChannel.getRemoteAddress(), e);
                // 取消注册
                selectionKey.cancel();
                // 关闭通道
                socketChannel.close();
            } catch (Exception ex) {
                log.error("客户端下线 处理异常", ex);
            }
        }
        return baos.toString(StandardCharsets.UTF_8);
    }

    private void forwardInfo(String msg, SocketChannel self) {
        if (StringUtils.isBlank(msg)) {
            return;
        }
        try {
            log.info("{} 客户端发生消息 {}", self.getRemoteAddress(), msg);
            Set<SelectionKey> keys = selector.keys();
            for (SelectionKey selectionKey : keys) {
                SelectableChannel channel = selectionKey.channel();
                if (!(channel instanceof SocketChannel socketChannel) || channel == self) {
                    continue;
                }
                socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
            }
        } catch (Exception e) {
            log.error("服务端 转发消息异常 {}", msg, e);
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

}
