package com.cqust.service.netty.chatGroup;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.concurrent.ImmediateEventExecutor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author Ltian
 * @date 2025/8/27 16:58
 * @description:
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel nowChannel = ctx.channel();
        channelGroup.forEach(channel -> {
            if (channel != nowChannel) {
                String info = "客户端:" + ctx.channel().remoteAddress() + " 发送消息: " + msg;
                channel.writeAndFlush(info);
            }
        });
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String info = "客户端:" + ctx.channel().remoteAddress() + " 上线了...";
        channelGroup.writeAndFlush(info);
        channelGroup.add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String info = "客户端:" + ctx.channel().remoteAddress() + " 下线了...";
        channelGroup.writeAndFlush(info);
        channelGroup.remove(ctx.channel());
        log.info("channelInactive channel数量 {}", channelGroup.size());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("服务端连接异常", cause);
        ctx.close();
    }
}
