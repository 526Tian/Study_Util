package com.cqust.service.netty.doubborpc.provider;

import com.cqust.service.netty.doubborpc.HelloService;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Ltian
 * @date 2025/8/30 17:45
 * @description:
 */
@Slf4j
public class NettyClient {

    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static NettyClientHandler nettyClientHandler = new NettyClientHandler();

    private static final String PRE_PROTO = "HelloService#sayHello#";

    public static void main(String[] args) {
        run();
        HelloService bean = (HelloService)getBean(HelloService.class, PRE_PROTO);
        String s = bean.sayHello("你好啊，我是NettyClient");
        log.info("NettyClient main方法收到结果 {}", s);
    }

    public static Object getBean(final Class<?> serviceClass, final String providerName) {
        return Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class[]{serviceClass},
                (proxy, method, args) -> {
                    nettyClientHandler.setPreProto(PRE_PROTO + args[0]);
                    return executorService.submit(nettyClientHandler).get();
                });
    }

    public static void run() {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventExecutors)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(nettyClientHandler);
                        }
                    });
            ChannelFuture sync = bootstrap.connect(new InetSocketAddress("127.0.0.1", 7000)).sync();
            log.info("netty客户端已就绪...");
        } catch (Exception e) {
            log.error("客户端异常", e);
        }
//        finally {
//            eventExecutors.shutdownGracefully();
//        }
    }

}
