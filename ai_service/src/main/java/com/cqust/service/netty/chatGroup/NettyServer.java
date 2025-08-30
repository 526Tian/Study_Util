package com.cqust.service.netty.chatGroup;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ltian
 * @date 2025/8/27 16:47
 * @description:
 */
@Slf4j
public class NettyServer {

    private Integer port;

    public NettyServer(Integer port) {
        this.port = port;
    }

    public void run() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("stringEncode", new StringEncoder());
                            pipeline.addLast("stringDecode", new StringDecoder());
                            pipeline.addLast("myServerHandler", new NettyServerHandler());
                        }
                    });

            ChannelFuture sync = serverBootstrap.bind(port).sync();
            log.info("netty服务端已就绪...");

            sync.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("netty服务端异常", e);
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer(7000);
        nettyServer.run();
    }

}
