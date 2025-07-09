package com.learn.store;

import com.learn.mongopo.HistoryChatMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName -> MongoDBChatMemoryStore
 * @Description
 * @Author soso
 * @Date 2025/7/2 17:56 星期三
 * @Version 1.0
 */
@Component
public class MongoDBChatMemoryStore implements ChatMemoryStore {

    /**
     * mongoDb模板
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        HistoryChatMessage historyChatMessage =
                mongoTemplate.findOne(Query.query(Criteria.where("chatId").is(memoryId.toString())), HistoryChatMessage.class);
        if (historyChatMessage != null) {
            String content = historyChatMessage.getContent();
            if (StringUtils.isBlank(content)) {
                return new LinkedList<>();
            }
            return ChatMessageDeserializer.messagesFromJson(content);
        }
        return new LinkedList<>();
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        if (messages == null) {
            messages = new LinkedList<>();
        }
        Query query = Query.query(Criteria.where("chatId").is(memoryId.toString()));
        Update update = new Update().set("content", ChatMessageSerializer.messagesToJson(messages));
        mongoTemplate.upsert(query, update, HistoryChatMessage.class);
    }

    @Override
    public void deleteMessages(Object memoryId) {
        Criteria criteria = Criteria.where("chatId").is(memoryId.toString());
        Query query = Query.query(criteria);
        mongoTemplate.remove(query);
    }
}
