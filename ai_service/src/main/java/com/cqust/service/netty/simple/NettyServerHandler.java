package com.cqust.service.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author Ltian
 * @date 2025/8/25 17:09
 * @description:
 */

/**
 * 我们自定一个channelHandler 需要继承netty规定好的某个handler适配器
 */
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer(10);
        for (int i = 0; i < 5; ++ i) {
            buffer.writeByte(i);
        }
        for (int i = 0; i < buffer.capacity(); ++ i) {
            System.out.println(buffer.readByte());
        }
        byte[] array = buffer.array();
        int i = buffer.readableBytes();
//        buffer.getCharSequence();
    }

    /**
     * 读取数据事件（可以读取客户端发送的消息）
     * @param ctx 上下文对象 含有 pipeline 管道 channel 通道 地址
     * @param msg 客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.channel().eventLoop().execute(() -> {
            try {
                Thread.sleep(1000 * 10);
                log.info("10秒任务已完成... {}]", new Date());
            } catch (Exception e) {

            }
        });
        ByteBuf byteBuf = (ByteBuf) msg;
        log.info("sever ctx {} msg {} client {} {}", ctx, byteBuf.toString(CharsetUtil.UTF_8), ctx.channel().remoteAddress(), new Date());
    }

    /**
     * 读取消息完毕
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // writeFlush 是write + flush 将数据写入缓存中 并刷新
        // 一般讲 需要对发送的这个数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端...", CharsetUtil.UTF_8));
    }

    /**
     * 处理异常 需要关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("服务端异常", cause);
        ctx.close();
    }
}
