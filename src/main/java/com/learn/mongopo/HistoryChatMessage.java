package com.learn.mongopo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @ClassName -> HistoryChatMessage
 * @Description
 * @Author soso
 * @Date 2025/7/2 18:06 星期三
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "history_chat_message")
public class HistoryChatMessage {
    @Id
    private ObjectId id;

    private String chatId;

    private String content;
}
