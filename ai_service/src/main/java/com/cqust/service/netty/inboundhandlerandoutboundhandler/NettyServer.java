package com.cqust.service.netty.inboundhandlerandoutboundhandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ltian
 * @date 2025/8/28 15:51
 * @description:
 */
@Slf4j
public class NettyServer {

    public static void main(String[] args) {
        // 创建BossGroup和workGroup
        // bossGroup只处理连接请求 业务处理交给workGroup
        // 两个都是无限循环
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            // 创建服务器端的启动对象，配置参数
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // 创建一个通道测试对象
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new MyByteToLongDecoder());
                            pipeline.addLast(new MyLongToByteEncoder());
                            pipeline.addLast(new NettyServerHandler());
                        }
                    }); // 给workGroup EventGroup对应的管道设置处理器

            log.info("服务器已就绪...");

            // 绑定一个端口并同步生成了 一个channelFuture对象 启动服务器
            ChannelFuture sync = serverBootstrap.bind(7000).sync();

            // 对关闭通道进行监听
            sync.channel().closeFuture().sync();
        } catch (Exception e) {
            log.error("netty服务端异常", e);
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

}
