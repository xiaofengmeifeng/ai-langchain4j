package com.learn.assistant;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;

/**
 * @ClassName -> MongoDbChatMemoryAssistant
 * @Description 智能助理，需要明确指出chatModel、chatMemoryProvider，可以实现聊天极易隔离
 * @Author soso
 * @Date 2025/7/2 11:20 星期三
 * @Version 1.0
 */
@AiService(wiringMode = AiServiceWiringMode.EXPLICIT,
        chatModel = "qwenChatModel",
        chatMemoryProvider = "mongodbMessageWindowChatMemory"
)
public interface MongoDbChatMemoryAssistant {
    /**
     * 聊天方法
     *
     * @param memoryId    聊天记忆id
     * @param userMessage 用户消息
     * @return 聊天响应
     */
    @SystemMessage("你是一个正宗的四川人，是我的好朋友，请你使用四川话回答问题。")
    String chat(@MemoryId String memoryId, @UserMessage String userMessage);

    /**
     * 聊天方法。
     * 使用用户提示词模板，用户提示词模板文件放在resources下，文件中的参数使用@V注解指定，提示词中的参数使用{{参数名}}指定，
     * 用户提示词每次都会被追加到聊天消息中，
     * 多个用户提示词参数使用@V注解指定，参数名使用@V("参数名")指定。
     *
     * @param memoryId    聊天记忆id
     * @param userMessage 用户消息
     * @param username    用户名
     * @param age         用户年龄
     * @return 聊天响应
     * @SystemMessage 系统消息，用于指定系统角色
     */
    @UserMessage(fromResource = "user-prompt-template.txt")
    String chat4UserMessage(@MemoryId String memoryId, @V("userMessage") String userMessage, @V("username") String username, @V("age") Integer age);

    /**
     * 聊天方法。
     * 使用系统提示词模板，系统提示词模板文件放在resources下，文件中的参数使用@V注解指定，提示词中的参数使用{{参数名}}指定，
     * 系统提示词仅出现1次，如果系统提示词发生改变，则会重新生成系统提示词，系统提示词会被追加到聊天消息中，
     * 多个系统提示词参数使用@V注解指定，参数名使用@V("参数名")指定。
     *
     * @param memoryId    聊天记忆id
     * @param userMessage 用户消息
     * @param username    用户名
     * @param age         用户年龄
     * @return 聊天响应
     * @SystemMessage 系统消息，用于指定系统角色
     */
    @SystemMessage(fromResource = "system-prompt-template.txt")
    String chat4SystemMessage(@MemoryId String memoryId, @UserMessage String userMessage, @V("username") String username, @V("age") Integer age);
}
