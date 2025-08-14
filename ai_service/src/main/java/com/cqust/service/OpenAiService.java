package com.cqust.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import reactor.core.publisher.Mono;

/**
 * @author Ltian
 * @date 2025/8/13 17:51
 * @description:
 */
public interface OpenAiService {

    Mono<String> processorOpenAiInterface();

}
