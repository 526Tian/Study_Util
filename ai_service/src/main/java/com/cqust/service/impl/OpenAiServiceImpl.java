package com.cqust.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.cqust.service.OpenAiService;

import java.io.OutputStream;
import java.net.Socket;

/**
 * @author Ltian
 * @date 2025/8/14 11:06
 * @description:
 */
@Service
@Slf4j
public class OpenAiServiceImpl implements OpenAiService {

    @Resource
    private WebClient webClient;

    @Resource
    private ObjectMapper objectMapper;

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 6666);
        OutputStream out = socket.getOutputStream();
        out.write("hello".getBytes());
        out.flush();
        socket.close();
    }

    @Override
    public Mono<String> processorOpenAiInterface(){

        String body = "{\n" +
                "    \"model\": \"gpt-4o-mini\",\n" +
                "    \"input\": \"你是什么模型，宝贝\",\n" +
                "    \"store\": true\n" +
                "}";

        Mono<String> stringMono = webClient.post()
                .uri("https://api.openai.com/v1/responses")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(result -> {
                    JsonNode jsonNode = null;
                    try {
                        jsonNode = objectMapper.readTree(result);
                        log.info("openAi结果 {}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                });
        log.info("非阻塞流程");

        return stringMono;
    }

}
