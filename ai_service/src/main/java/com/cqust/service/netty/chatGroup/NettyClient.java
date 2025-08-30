package com.cqust.service.netty.chatGroup;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * @author Ltian
 * @date 2025/8/27 17:07
 * @description:
 */
@Slf4j
public class NettyClient {

    private String host;

    private Integer port;

    public NettyClient(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("stringDecode", new StringDecoder());
                            pipeline.addLast("stringEncode", new StringEncoder());
                            pipeline.addLast("myServerHandler", new NettyClientHandler());
                        }
                    });

            ChannelFuture sync = bootstrap.connect(new InetSocketAddress(host, port)).sync();
            Channel channel = sync.channel();
            log.info("netty客户端已启动 {}", channel.localAddress());

            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()) {
                String msg = scanner.nextLine();
                channel.writeAndFlush(msg);
            }

        } catch (Exception e) {
            log.error("netty客户端异常", e);
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient("127.0.0.1", 7000);
        nettyClient.run();
    }

}
