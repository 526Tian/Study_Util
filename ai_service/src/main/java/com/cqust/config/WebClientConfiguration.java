package com.cqust.config;

import com.cqust.convert.HttpClientConfigConvert;
import com.cqust.http.util.WebClientGeneration;
import jakarta.annotation.Resource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Ltian
 * @date 2025/8/13 17:45
 * @description:
 */
@EnableConfigurationProperties(value = {HttpClientInfoConfig.class})
@Configuration
public class WebClientConfiguration {

    @Resource
    private HttpClientConfigConvert httpClientConfigConvert;

    @Bean
    public WebClient webClient(HttpClientInfoConfig httpClientInfoConfig) {
        return WebClientGeneration.generationHttpClient(httpClientConfigConvert.httpClientInfoConfigConvertHttpConfig(httpClientInfoConfig));
    }

}
