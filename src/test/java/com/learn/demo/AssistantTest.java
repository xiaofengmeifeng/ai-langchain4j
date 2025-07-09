package com.learn.demo;

import com.learn.assistant.Assistant;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.spring.AiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName -> AssistantTest
 * @Description
 * @Author soso
 * @Date 2025/7/2 10:43 星期三
 * @Version 1.0
 */
@SpringBootTest
public class AssistantTest {

    /**
     * 注入通义Qwen chatModel
     */
    @Autowired
    @Qualifier("qwenChatModel")
    private ChatModel qwenChatModel;

    /**
     * 测试Assistant
     */
    @Test
    public void testAssistant(){
        Assistant assistant = AiServices.create(Assistant.class, qwenChatModel);
        String res = assistant.chat("你好");
        System.out.println(res);
    }


    @Autowired
    private Assistant assistant;

    @Test
    public void testAssistantSpringBoot(){
        String res = assistant.chat("你好");
        System.out.println(res);
    }

}
