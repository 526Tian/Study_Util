package com.cqust.service.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ltian
 * @date 2025/8/28 15:57
 * @description:
 */
@Slf4j
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Long aLong, ByteBuf byteBuf) throws Exception {
        log.info("MyLongToByteEncoder触发 写入内容 {}", aLong);
        byteBuf.writeLong(aLong);
    }

}
