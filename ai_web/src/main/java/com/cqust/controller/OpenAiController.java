package com.cqust.controller;

import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cqust.service.OpenAiService;
import reactor.core.publisher.Mono;

/**
 * @author Ltian
 * @date 2025/8/13 17:49
 * @description:
 */
@RestController
@RequestMapping("/openAi")
@Slf4j
public class OpenAiController {

    @Resource
    private OpenAiService openAiService;

    @GetMapping("/test")
    public Mono<String> test() {
        log.info("对话开始了");
        return openAiService.processorOpenAiInterface();
    }

}
