package com.learn.assistant;

import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

/**
 * @ClassName -> ChatMemoryAssistant
 * @Description 智能助理，需要明确指出chatModel、chatMemory
 * @Author soso
 * @Date 2025/7/2 11:20 星期三
 * @Version 1.0
 */
@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "qwenChatModel",
        chatMemory = "messageWindowChatMemory"
)
public interface ChatMemoryAssistant {
    String chat(String userMessage);
}
