package com.cqust.service.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Ltian
 * @date 2025/8/28 15:59
 * @description:
 */
@Slf4j
public class MyByteToLongDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        log.info("MyLongToByteEncoder 编码触发");
        if (byteBuf.readableBytes() >= 8) {
            long l = byteBuf.readLong();
            log.info("MyLongToByteEncoder编码触发 读取内容 {}", l);
            list.add(l);
        }
    }

}
