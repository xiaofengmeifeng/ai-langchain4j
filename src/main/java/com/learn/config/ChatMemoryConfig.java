package com.learn.config;

import com.learn.store.MongoDBChatMemoryStore;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName -> ChatMemoryConfig
 * @Description 聊天记忆配置
 * @Author soso
 * @Date 2025/7/2 16:32 星期三
 * @Version 1.0
 */
@Configuration
public class ChatMemoryConfig {

    /**
     * 消息窗口聊天记忆配置，最多可以存储10条聊天极易
     *
     * @return 消息窗口聊天记忆
     */
    @Bean
    public ChatMemory messageWindowChatMemory() {
        return MessageWindowChatMemory.builder().maxMessages(10).build();
    }

    /**
     * 分区消息窗口聊天记忆配置，最多可以存储10条聊天极易，每个用户都有自己的聊天记忆
     *
     * @return 分区消息窗口聊天记忆
     */
    @Bean
    public ChatMemoryProvider partitionMessageWindowChatMemory() {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .chatMemoryStore(new InMemoryChatMemoryStore())
                .maxMessages(10)
                .build();
    }

    /**
     * mongodb聊天记忆配置，最多可以存储10条聊天极易，每个用户都有自己的聊天记忆
     * @param mongoDBChatMemoryStore mongodb聊天记忆存储
     * @return mongodb聊天记忆
     */
    @Bean
    public ChatMemoryProvider mongodbMessageWindowChatMemory(MongoDBChatMemoryStore mongoDBChatMemoryStore) {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(10)
                .chatMemoryStore(mongoDBChatMemoryStore)
                .build();
    }
}
