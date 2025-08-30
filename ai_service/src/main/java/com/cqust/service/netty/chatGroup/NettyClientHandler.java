package com.cqust.service.netty.chatGroup;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ltian
 * @date 2025/8/27 17:20
 * @description:
 */
@Slf4j
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("{} 客户端收到消息 {}", ctx.channel().localAddress(), msg);
    }
}
