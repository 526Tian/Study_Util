package com.cqust.service.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import java.net.URI;

/**
 * @author Ltian
 * @date 2025/8/25 23:09
 * @description:
 */
@Slf4j
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest httpRequest) {

            String uri = httpRequest.uri();
            URI uriObject = new URI(uri);
            String path = uriObject.getPath();
            if (!"/".equals(uri)) {
                log.info("favicon.ico 请求拦截掉 url {}", uri);
                return;
            }

            log.info("msg 类型 {} uri {} 客户端地址 {}", msg.getClass(), uri, ctx.channel().remoteAddress());

            ByteBuf byteBuf = Unpooled.copiedBuffer("你好, 这是服务器...".getBytes(CharsetUtil.UTF_8));

            DefaultFullHttpResponse defaultHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);

            defaultHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
                    .set(HttpHeaderNames.CONTENT_LENGTH, byteBuf.readableBytes());

//            defaultHttpResponse.hea

            ctx.writeAndFlush(defaultHttpResponse);
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        ChannelGroup channelGroup = null;
//        channelGroup.forEach();
        log.info("handlerAdded触发了");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("handlerRemoved触发了");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelActive触发了");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("channelInactive触发了");
    }
}
