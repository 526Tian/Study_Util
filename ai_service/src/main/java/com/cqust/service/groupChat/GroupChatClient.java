package com.cqust.service.groupChat;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Ltian
 * @date 2025/8/22 22:32
 * @description:
 */
@Slf4j
public class GroupChatClient {

    private SocketChannel socketChannel;

    private Selector selector;

    private String userName;

    private static final String HOST = "127.0.0.1";

    private static final Integer PORT = 7000;

    public GroupChatClient() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            userName = socketChannel.getLocalAddress().toString().substring(1);
            log.info("{} 客户端已准备就绪", userName);
        } catch (Exception e) {
            log.error("初始化客户端异常", e);
        }
    }

    public void sendInfo(String msg) {
        String info = userName + " 说: " + msg;
        try {
            byte[] bytes = info.getBytes();
            ByteBuffer buffer = ByteBuffer.allocate(16);
            int offset = 0;
            while(offset < bytes.length) {
                int length = Math.min(buffer.capacity(), bytes.length - offset);
                buffer.clear();
                buffer.put(bytes, offset, length);
                buffer.flip();
                while(buffer.hasRemaining()) {
                    socketChannel.write(buffer);
                }
                offset += length;
            }
        } catch (Exception e) {
            log.error("{} 客户端发送消息异常", userName, e);
        }
    }

    public void readInfo() {
        try {
            while (true) {
                if (selector.select(2000) == 0) {
                    continue;
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isReadable()) {
                        String msg = getBufferInfo(selectionKey, socketChannel);
                        if (StringUtils.isNotBlank(msg)) {
                            log.info("{} 客户端接收到消息 {}", userName, msg);
                        }
                        iterator.remove();
                    }
                }
            }
        } catch (Exception e) {
            log.error("{} 客户端接受消息异常", userName, e);
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
                log.error("{} 服务端已下线", socketChannel.getRemoteAddress(), e);
                // 取消注册
                selectionKey.cancel();
                // 关闭通道
                socketChannel.close();
            } catch (Exception ex) {
                log.error("服务端下线 处理异常", ex);
            }
        }
        return baos.toString(StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        GroupChatClient groupChatClient = new GroupChatClient();
        new Thread(groupChatClient::readInfo).start();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String msg = scanner.nextLine();
            log.info("输出msg {}", msg);
            groupChatClient.sendInfo(msg);
        }
    }

}
