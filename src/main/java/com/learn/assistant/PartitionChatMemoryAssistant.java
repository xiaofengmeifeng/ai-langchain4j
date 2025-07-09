package com.learn.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

/**
 * @ClassName -> ChatMemoryAssistant
 * @Description 智能助理，需要明确指出chatModel、chatMemoryProvider，可以实现聊天极易隔离
 * @Author soso
 * @Date 2025/7/2 11:20 星期三
 * @Version 1.0
 */
@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "qwenChatModel",
        chatMemoryProvider = "partitionMessageWindowChatMemory",
        tools = {
                "calculateTool"
        }
)
public interface PartitionChatMemoryAssistant {
    /**
     * 聊天方法
     * @SystemMessage 系统消息，用于指定系统角色
     * @param memoryId 聊天记忆id
     * @param userMessage 用户消息
     * @return 聊天响应
     */
    @SystemMessage("你是一个正宗的四川人，请你使用四川话回答问题。")
    String chat(@MemoryId String memoryId, @UserMessage String userMessage);
}
