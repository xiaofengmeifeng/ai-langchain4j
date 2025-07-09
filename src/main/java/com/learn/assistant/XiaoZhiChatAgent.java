package com.learn.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;
import reactor.core.publisher.Flux;

/**
 * 小智医疗助手智能体
 */
@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT,
//        chatModel = "qwenChatModel",
        streamingChatModel = "qwenStreamingChatModel",
        chatMemoryProvider = "xiaoZhiMessageWindowChatMemory",
        tools = {
                "appointmentTools"
        },
//        contentRetriever = "contentRetrieverXiaoZhi"
        contentRetriever = "contentRetriever4Pinecone"
)
public interface XiaoZhiChatAgent {

    /**
     * 聊天方法
     *
     * @param memoryId    聊天记忆id
     * @param userMessage 用户消息
     * @return 聊天响应
     */
    @SystemMessage(fromResource = "xiaozhi-prompt-template.txt")
    String chat(@MemoryId String memoryId, @UserMessage String userMessage);

    /**
     * 聊天方法流式输出
     *
     * @param memoryId    聊天记忆id
     * @param userMessage 用户消息
     * @return 聊天响应
     */
    @SystemMessage(fromResource = "xiaozhi-prompt-template.txt")
    Flux<String> chat4Flux(@MemoryId String memoryId, @UserMessage String userMessage);
}
