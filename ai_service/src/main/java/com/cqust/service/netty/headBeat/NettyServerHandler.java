package com.cqust.service.netty.headBeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ltian
 * @date 2025/8/27 18:12
 * @description:
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("{} 客户端上线了", ctx.channel().remoteAddress());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent idleStateEvent) {
            String info = "";
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    info = "读空闲事件";
                    break;
                case WRITER_IDLE:
                    info = "写空闲事件";
                    break;
                case ALL_IDLE:
                    info = "读写空闲事件";
                    break;
                default:
                    info = "未知事件";
            }
            log.info("客户端 {} 触发事件 {}", ctx.channel().remoteAddress(), info);
            ctx.channel().close();
        }
    }

}
