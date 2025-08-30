package com.cqust.service.netty.tcp;

import com.cqust.service.netty.inboundhandlerandoutboundhandler.MyByteToLongDecoder;
import com.cqust.service.netty.inboundhandlerandoutboundhandler.MyLongToByteEncoder;
import com.cqust.service.netty.inboundhandlerandoutboundhandler.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author Ltian
 * @date 2025/8/28 17:23
 * @description:
 */
@Slf4j
public class TcpNettyClient {

    public static void main(String[] args) {
        // 客户端需要一个事件循环组
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new TcpStringToByteEncoder());
                            pipeline.addLast(new TcpByteToStringDecoder());
                            pipeline.addLast(new TcpNettyClientHandler());
                        }
                    });
            log.info("客户端已准备就绪...");
            ChannelFuture sync = bootstrap.connect(new InetSocketAddress("127.0.0.1", 7000)).sync();
            sync.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("netty客户端异常", e);
        }finally {
            eventExecutors.shutdownGracefully();
        }
    }

}
