package com.example.supportclassifier.config;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class AIConfig {

    @Bean
    public ChatLanguageModel chatLanguageModel(
            @Value("${ollama.base-url:http://localhost:11434}") String baseUrl,
            @Value("${ollama.model-name:llama3.2}") String modelName,
            @Value("${ollama.timeout-seconds:120}") long timeoutSeconds) {

        return OllamaChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(modelName)
                .timeout(Duration.ofSeconds(Math.max(10, timeoutSeconds)))
                .temperature(0.2)
                .build();
    }
}
