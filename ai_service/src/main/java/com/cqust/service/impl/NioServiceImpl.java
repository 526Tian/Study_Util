package com.cqust.service.impl;

import com.cqust.service.NioService;
import lombok.extern.slf4j.Slf4j;

import java.nio.CharBuffer;
import java.nio.IntBuffer;

/**
 * @author Ltian
 * @date 2025/8/17 17:31
 * @description:
 */
@Slf4j
public class NioServiceImpl implements NioService {

    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);

        for (int i = 0; i < intBuffer.capacity(); ++ i) {
            intBuffer.put(i * 2);
        }

        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            log.info("数据为 {}", intBuffer.get());
        }
    }

}
