package com.learn.config;

import com.learn.store.MongoDBChatMemoryStore;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName -> XiaoZhiChatConfig
 * @Description
 * @Author soso
 * @Date 2025/7/3 17:15 星期四
 * @Version 1.0
 */
@Configuration
public class XiaoZhiChatConfig {

    /**
     * 构建小智医疗助手使用的聊天记忆配置对象
     *
     * @param mongoDBChatMemoryStore mongodb聊天记忆存储
     * @return 小智医疗助手使用的聊天记忆配置对象
     */
    @Bean
    public ChatMemoryProvider xiaoZhiMessageWindowChatMemory(MongoDBChatMemoryStore mongoDBChatMemoryStore) {
        return memoryId -> MessageWindowChatMemory.builder()
                .id(memoryId)
                .maxMessages(10)
                .chatMemoryStore(mongoDBChatMemoryStore)
                .build();
    }

}
