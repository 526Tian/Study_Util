package com.cqust.service.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ltian
 * @date 2025/8/28 16:09
 * @description:
 */
@Slf4j
public class NettyClientHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        log.info("{} 客户端收到消息 {}", ctx.channel().localAddress(), msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("链接成功...");
        ctx.channel().writeAndFlush(123L);
    }
}
