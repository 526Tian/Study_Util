package com.cqust.service.netty.doubborpc.provider;

import com.cqust.service.netty.doubborpc.HelloService;
import com.cqust.service.netty.doubborpc.impl.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ltian
 * @date 2025/8/30 17:31
 * @description:
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    private static final String PRE_PROTO = "HelloService#sayHello#";

    private static final String SEPARATOR = "#";

    private final HelloService helloService = new HelloServiceImpl();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        log.info("服务端handler收到消息 {}", msg);
        if (msg.startsWith(PRE_PROTO)) {
            String returnValue = helloService.sayHello(msg.substring(msg.lastIndexOf(SEPARATOR)));
            ctx.writeAndFlush(returnValue);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("netty服务端channelInactive");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("服务端 socketChannel连接异常", cause);
        ctx.channel().close();
    }
}
