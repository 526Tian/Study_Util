package com.cqust.service.netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ltian
 * @date 2025/8/27 21:27
 * @description:
 */
@Slf4j
public class WebSocketNettyServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("服务器收到消息 {}", msg.text());
        ctx.channel().writeAndFlush(new TextWebSocketFrame("客户端收到消息: " + msg.text()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("WebSocketNettyServerHandler执行异常", cause);
    }
}
