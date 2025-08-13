package com.cqust.demo;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatCompletion;
import com.openai.models.ChatCompletionCreateParams;
import com.openai.models.ChatModel;

public class Main {

    public static void main(String[] args) {
        OpenAIClient client = OpenAIOkHttpClient.fromEnv();

        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .addUserMessage("你是什么模型，宝贝")
                .model(ChatModel.GPT_4O_MINI)
                .build();
        ChatCompletion chatCompletion = client.chat().completions().create(params);
        System.out.println("完成");
    }

}