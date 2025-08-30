package com.cqust.service.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author Ltian
 * @date 2025/8/28 18:29
 * @description:
 */
@Slf4j
public class TcpStringToByteEncoder extends MessageToByteEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, String msg, ByteBuf byteBuf) throws Exception {
        byte[] bytes = msg.getBytes(CharsetUtil.UTF_8);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }
}
