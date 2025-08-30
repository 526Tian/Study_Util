package com.cqust.service.netty.doubborpc.provider;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

/**
 * @author Ltian
 * @date 2025/8/30 17:53
 * @description:
 */
@Slf4j
public class NettyClientHandler extends SimpleChannelInboundHandler<String> implements Callable {

    private static ChannelHandlerContext channelHandlerContext;

    private String result;

    private String preProto;

    @Override
    protected synchronized void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("netty客户端收到消息 {}", msg);
        result = msg;
        notify();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("netty客户端已连接");
        channelHandlerContext = ctx;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("netty客户端连接异常", cause);
        ctx.channel().close();
    }

    @Override
    public synchronized Object call() throws Exception {
        channelHandlerContext.writeAndFlush(preProto);
        wait();
        log.info("call接口收到返回值 {}", result);
        return result;
    }

    public void setPreProto(String preProto) {
        this.preProto = preProto;
    }

}
