package com.learn.repository;

import com.learn.mongopo.HistoryChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
/**
 * @ClassName -> HistoryChatMessageRepository
 * @Description 历史聊天消息存储库，用于存储历史聊天消息
 * @Author soso
 * @Date 2025/7/2 17:58 星期三
 * @Version 1.0
 */
public interface HistoryChatMessageRepository extends MongoRepository<HistoryChatMessage, String> {
}
