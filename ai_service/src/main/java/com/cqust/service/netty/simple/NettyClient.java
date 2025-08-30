package com.cqust.service.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author Ltian
 * @date 2025/8/25 17:26
 * @description:
 */
@Slf4j
public class NettyClient {

    public static void main(String[] args) throws Exception{
        // 客户端需要一个事件循环组
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    });
            log.info("客户端已准备就绪...");
            ChannelFuture sync = bootstrap.connect(new InetSocketAddress("127.0.0.1", 7000)).sync();
            sync.channel().closeFuture().sync();
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }

}
