package com.cqust.service.netty.inboundhandlerandoutboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ltian
 * @date 2025/8/28 16:02
 * @description:
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
      log.info(" {} 服务端 读取到数据 {}", ctx.channel().remoteAddress(), msg);
      ctx.channel().writeAndFlush(789L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("连接异常 {} 服务端", ctx.channel().remoteAddress(), cause);
        ctx.channel().close();
    }
}
