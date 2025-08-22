package com.cqust.service.impl;

import com.cqust.service.BioService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Ltian
 * @date 2025/8/17 16:07
 * @description:
 */
@Slf4j
public class BioServiceImpl implements BioService {

    public static void main(String[] args) throws Exception{
        ExecutorService executorService = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(6666)) {
            while (true) {
                log.info("等待连接...");
                Socket socket = serverSocket.accept();
                log.info("连接到一个客户端 ");
                executorService.execute(() -> {
                    readSocketInfo(socket);
                });
            }
        } catch (Exception e) {
            log.info("socket连接异常", e);
        }

    }

    public static void readSocketInfo(Socket socket) {
        byte[] bytes = new byte[1024];
        try (socket) {
            InputStream inputStream = socket.getInputStream();
            while (true) {
                int read = inputStream.read(bytes);
                if (read == -1) {
                    break;
                } else {
                    log.info("读取数据为 {}", new String(bytes, 0, read));
                }
            }
            log.info("客户端关闭...");
        } catch (IOException e) {
            log.error("关闭socket异常....", e);
        }

    }

}
