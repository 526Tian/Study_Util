package com.cqust.service.netty.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpServerCodec;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ltian
 * @date 2025/8/25 23:09
 * @description:
 */
@Slf4j
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("myHttpServerCodec", new HttpServerCodec());
        pipeline.addLast("myHttpServerHandler", new TestHttpServerHandler());
        log.info("pipeline加入handler成功");
    }

}
