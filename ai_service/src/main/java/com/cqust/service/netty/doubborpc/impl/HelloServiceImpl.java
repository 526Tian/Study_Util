package com.cqust.service.netty.doubborpc.impl;

import com.cqust.service.netty.doubborpc.HelloService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ltian
 * @date 2025/8/30 17:24
 * @description:
 */
@Slf4j
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String msg) {
        log.info("服务端收到数据 {}", msg);
        return "服务端 发送数据: " + msg;
    }
}
