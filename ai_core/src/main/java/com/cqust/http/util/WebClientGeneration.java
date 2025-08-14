package com.cqust.http.util;

import com.cqust.http.config.HttpConfig;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.transport.ProxyProvider;

import java.time.Duration;

/**
 * @author Ltian
 * @date 2025/8/13 17:46
 * @description:
 */
public class WebClientGeneration {

    public static WebClient generationHttpClient(HttpConfig httpConfig) {
        // 连接池配置
        ConnectionProvider custom = ConnectionProvider.builder("custom")
                .maxConnections(httpConfig.getMaxConnection())
                .pendingAcquireTimeout(Duration.ofSeconds(httpConfig.getPendingAcquireTimeout()))
                .maxIdleTime(Duration.ofSeconds(httpConfig.getMaxIdleTime()))
                .build();

        // httpClient 配置
        HttpClient httpClient = HttpClient.create(custom)
                .proxy(proxy -> proxy
                        .type(ProxyProvider.Proxy.HTTP) // 如果是 HTTP 代理
                        .host("127.0.0.1")
                        .port(7890))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, httpConfig.getConnectTime())
                .responseTimeout(Duration.ofSeconds(httpConfig.getSocketTime()))
                .doOnConnected(conn -> {
                    conn.addHandlerFirst(new ReadTimeoutHandler(httpConfig.getReadTime()))
                            .addHandlerLast(new WriteTimeoutHandler(httpConfig.getWriteTime()));
                });

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeaders(defaultHeader ->  defaultHeader.setAll(httpConfig.getHeaderMap()))
                .build();
    }

}
