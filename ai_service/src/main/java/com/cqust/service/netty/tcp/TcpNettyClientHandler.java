package com.cqust.service.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author Ltian
 * @date 2025/8/28 17:24
 * @description:
 */
@Slf4j
public class TcpNettyClientHandler extends SimpleChannelInboundHandler<String> {

    private int count = 0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("客户端 第{}次 收到消息 {}", ++ count, msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端 {} 连接服务端 {}成功", ctx.channel().localAddress(), ctx.channel().remoteAddress());
        for (int i = 0; i < 10; ++ i) {
            String info = "hello world " + i + "; ";
            ctx.channel().writeAndFlush(info);
        }
    }
}
