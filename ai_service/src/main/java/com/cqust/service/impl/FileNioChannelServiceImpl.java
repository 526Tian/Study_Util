package com.cqust.service.impl;

import cn.hutool.core.lang.hash.Hash;
import com.cqust.service.FileNioChannelService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Ltian
 * @date 2025/8/18 17:14
 * @description:
 */
@Slf4j
public class FileNioChannelServiceImpl implements FileNioChannelService {


    @SneakyThrows
    public static void main(String[] args){
        new Thread(FileNioChannelServiceImpl::selectorConnectionServer).start();
        Thread.sleep(1000);
        new Thread(FileNioChannelServiceImpl::selectorConnectionClient).start();
//        new Thread(FileNioChannelServiceImpl::selectorConnectionClient1).start();
//        Thread.sleep(1000);
    }

    public static void selectorConnectionClient1() {
        try (SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.configureBlocking(false);

            if (!socketChannel.connect(new InetSocketAddress("127.0.0.1", 7000))) {
                while (!socketChannel.finishConnect()) {
                    log.info("客户端连接失败... 非租塞");
                }
            }

            String txt = "hello world 小白1";
            ByteBuffer wrap = ByteBuffer.wrap(txt.getBytes());

            socketChannel.write(wrap);
            log.info("客户端发送信息完毕1");
            System.in.read();
        } catch (Exception e) {
            log.error("selector操作异常 客户端 ", e);
        }

    }

    public static void selectorConnectionClient() {
        try (SocketChannel socketChannel = SocketChannel.open()) {
            socketChannel.configureBlocking(false);

            if (!socketChannel.connect(new InetSocketAddress("127.0.0.1", 7000))) {
                while (!socketChannel.finishConnect()) {
                    log.info("客户端连接失败... 非租塞");
                }
            }

            String txt = "hello world 小白";
            ByteBuffer wrap = ByteBuffer.wrap(txt.getBytes());

            socketChannel.write(wrap);
            log.info("客户端发送信息完毕");
            Thread.sleep(3000);
            socketChannel.write(ByteBuffer.wrap("hello world 小明...".getBytes()));
        } catch (Exception e) {
            log.error("selector操作异常 客户端 ", e);
        }

    }

    public static void selectorConnectionServer() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.configureBlocking(false);

            serverSocketChannel.bind(new InetSocketAddress(7000));

            Selector selector = Selector.open();

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while(true) {

                if (selector.select(1000) == 0) {
                    log.info("等待1秒 没有事件发生");
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

                while(iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        SocketChannel accept = serverSocketChannel.accept();
                        accept.configureBlocking(false);
                        accept.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                        iterator.remove();
                    }

                    if (selectionKey.isReadable()) {
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                        int read = channel.read(byteBuffer);
                        if (read > 0) {
                            log.info("读到客户端内容 {} {} ", read, new String(byteBuffer.array()));
                        }
                        if (read == -1) {
                            log.info("客户端断开连接");
                            selectionKey.cancel();
                            channel.close();
                        }
                        byteBuffer.clear();
                        iterator.remove();
                    }
                }

            }

        } catch (Exception e) {
            log.error("slector操作异常", e);
        }
    }

    public static void serverSocketChannel() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
            serverSocketChannel.socket().bind(inetSocketAddress);
            SocketChannel accept = serverSocketChannel.accept();

            log.info("接收到客户端请求...");
            ByteBuffer[] byteBuffers = new ByteBuffer[2];
            byteBuffers[0] = ByteBuffer.allocate(5);
            byteBuffers[1] = ByteBuffer.allocate(3);

            int maxBufferLength = 8;
            while(true) {
                int readLength = 0;
                int writeLength = 0;

                while (readLength < maxBufferLength) {
                    long read = accept.read(byteBuffers);
                    readLength += read;
                    log.info("read字节数 {}", read);
                    Arrays.asList(byteBuffers).stream()
                            .map(byteBuffer -> "position = " + byteBuffer.position() + " limit = " + byteBuffer.limit())
                            .forEach(System.out::println);
                }

                Arrays.asList(byteBuffers).stream().forEach(ByteBuffer::flip);

                while (writeLength < maxBufferLength) {
                    long write = accept.write(byteBuffers);
                    writeLength += write;
                }

                Arrays.asList(byteBuffers).stream().forEach(ByteBuffer::clear);

                log.info("byteRead {} byteWrite {}", readLength, maxBufferLength);
            }

        } catch (Exception e) {
            log.error("serverSocketChannel操作异常", e);
        }
    }

    public static void mappedByteBuffer() {
        try (RandomAccessFile rw = new RandomAccessFile("f:\\file02.txt", "rw")) {

            FileChannel channel = rw.getChannel();
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

            map.put(0, (byte) 'H');
            map.put(3, (byte) '8');

            log.info("mappedByteBuffer修改成功");
        } catch (Exception e) {
            log.error("mappedByteBuffer拷贝异常", e);
        }

    }

    public static void transferFrom() {
        File file = new File("f:\\file01.txt");
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            FileChannel channel = fileInputStream.getChannel();

            try (FileOutputStream fileOutputStream = new FileOutputStream("f:\\file03.txt")) {
                FileChannel channel1 = fileOutputStream.getChannel();

               channel1.transferFrom(channel, 0, channel.size());
            } catch (Exception e) {
                log.error("fileChannel操作写入异常", e);
            }

        } catch (Exception e) {
            log.error("fileChannel操作读取异常", e);
        }
    }

    public static void channelCopy() {
        File file = new File("f:\\file01.txt");
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            FileChannel channel = fileInputStream.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            try (FileOutputStream fileOutputStream = new FileOutputStream("f:\\file02.txt")) {
                FileChannel channel1 = fileOutputStream.getChannel();

                while (true) {
                    byteBuffer.clear();
                    int read = channel.read(byteBuffer);
                    if (read == -1) {
                        break;
                    }
                    byteBuffer.flip();
                    channel1.write(byteBuffer);
                }
            } catch (Exception e) {
                log.error("fileChannel操作写入异常", e);
            }

        } catch (Exception e) {
            log.error("fileChannel操作读取异常", e);
        }
    }

    public static void channelRead() {
        File file = new File("f:\\file01.txt");
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            FileChannel channel = fileInputStream.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

            channel.read(byteBuffer);

            log.info("fileChannel操作读取数据为 {}", new String(byteBuffer.array()));

        } catch (Exception e) {
            log.error("fileChannel操作读取异常", e);
        }
    }

    public static void channelWrite() {
        String txt = "helloWorld 小白";
        try (FileOutputStream fileOutputStream = new FileOutputStream("f:\\file01.txt")) {
            FileChannel channel = fileOutputStream.getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            byteBuffer.put(txt.getBytes());

            byteBuffer.flip();

            channel.write(byteBuffer);

        } catch (Exception e) {
            log.error("fileChannel操作写入异常", e);
        }
    }

}
