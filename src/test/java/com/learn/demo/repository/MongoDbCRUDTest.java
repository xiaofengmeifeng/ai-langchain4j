package com.learn.demo.repository;

import com.learn.mongopo.HistoryChatMessage;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * @ClassName -> MongoDbCRUDTest
 * @Description
 * @Author soso
 * @Date 2025/7/3 12:46 星期四
 * @Version 1.0
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // 启用顺序控制
public class MongoDbCRUDTest {

    /**
     * mongoDb模板
     */
    @Autowired
    private MongoTemplate mongoTemplate;

    private String chatId = "68661346ccf43439d1eba777";

    /**
     * 测试mongoDb插入数据
     */
    @Test
    @Order(1)
    public void insertTest() {
        HistoryChatMessage historyChatMessage = new HistoryChatMessage(null, chatId, "[{\"role\":\"user\",\"content\":\"你好\"},{\"role\":\"assistant\",\"content\":\"你好，我是你的助手\"}]");
        HistoryChatMessage message = mongoTemplate.insert(historyChatMessage);
    }


    /**
     * 测试mongoDb更新数据
     */
    @Test
    @Order(2)
    public void updateTest() {
        mongoTemplate.updateFirst(Query.query(Criteria.where("chatId").is(chatId)), Update.update("content", "[{\"role\":\"user\",\"content\":\"你好\"},{\"role\":\"assistant\",\"content\":\"你好，我是你的助手\"},{\"role\":\"user\",\"content\":\"你好\"},{\"role\":\"assistant\",\"content\":\"你好，我是你的助手\"}]"), HistoryChatMessage.class);
    }

    /**
     * 测试mongoDb查询数据
     */
    @Test
    @Order(3)
    public void getByChatIdTest() {
        HistoryChatMessage historyChatMessage = mongoTemplate.findOne(Query.query(Criteria.where("chatId").is(chatId)), HistoryChatMessage.class);
        System.out.println(historyChatMessage);
    }

    /**
     * 测试mongoDb删除数据
     */
    @Test
    @Order(4)
    public void deleteByIdTest() {
        mongoTemplate.remove(Query.query(Criteria.where("chatId").is(chatId)), HistoryChatMessage.class);
    }

}
