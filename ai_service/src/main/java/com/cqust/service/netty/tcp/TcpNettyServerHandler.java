package com.cqust.service.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author Ltian
 * @date 2025/8/28 17:23
 * @description:
 */
@Slf4j
public class TcpNettyServerHandler extends SimpleChannelInboundHandler<String> {

    private int acceptMsgCount = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("服务端 第{}次 接收到 {} 客户端 发生的消息 {}", ++acceptMsgCount, ctx.channel().remoteAddress(), msg);
        ctx.channel().writeAndFlush(UUID.randomUUID() + "; ");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("连接异常 {} 服务端", ctx.channel().remoteAddress(), cause);
        ctx.channel().close();
    }
}
