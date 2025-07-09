package com.learn.demo;

import com.learn.assistant.Assistant;
import com.learn.assistant.ChatMemoryAssistant;
import com.learn.assistant.MongoDbChatMemoryAssistant;
import com.learn.assistant.PartitionChatMemoryAssistant;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.service.AiServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName -> ChatMemoryTest
 * @Description 聊天记忆测试类，主要用于做以下测试：
 * @Author soso
 * @Date 2025/7/2 11:08 星期三
 * @Version 1.0
 */
@SpringBootTest
public class ChatMemoryTest {

    @Autowired
    private Assistant assistant;

    /**
     * 测试是否具有对话记忆，测试结果：没有对话记忆
     */
    @Test
    public void test1() {
        String res = assistant.chat("我是秦始皇");
        System.out.println(res);

        String res1 = assistant.chat("我是谁？");
        System.out.println(res1);
    }

    @Autowired
    private ChatModel qwenChatModel;

    /**
     * 实现简单的对话记忆，测试对话记忆，测试结果：具有对话记忆，但是实现方式比较复杂
     */
    @Test
    public void test2() {
        UserMessage userMessage1 = new UserMessage("我是秦始皇");
        ChatResponse response = qwenChatModel.chat(userMessage1);
        AiMessage aiMessage = response.aiMessage();
        System.out.println(aiMessage.text());

        UserMessage userMessage2 = new UserMessage("我是谁？");
        ChatResponse response2 = qwenChatModel.chat(userMessage1, aiMessage, userMessage2);
        AiMessage aiMessage2 = response2.aiMessage();
        System.out.println(aiMessage2.text());
    }

    /**
     * 测试langchain4j的对话记忆，测试结果：具有对话记忆，实现方式比较简单，使用ChatMemory的方式实现，MessageWindowChatMemory对话消息窗口
     */
    @Test
    public void test3() {
        ChatMemory chatMemory = MessageWindowChatMemory.builder().maxMessages(10).build();
        Assistant assistant = AiServices.builder(Assistant.class).chatModel(qwenChatModel).chatMemory(chatMemory).build();
        String res = assistant.chat("我是秦始皇");
        System.out.println(res);
        String res1 = assistant.chat("我是谁？");
        System.out.println(res1);
    }

    /**
     * 实现了对话记忆的智能助理
     */
    @Autowired
    private ChatMemoryAssistant chatMemoryAssistant;

    /**
     * 测试langchain4j的对话记忆，测试结果：具有对话记忆，实现方式比较简单，使用ChatMemory的方式实现，
     * MessageWindowChatMemory对话消息窗口使用spring容器管理
     */
    @Test
    public void test4() {
        String res = chatMemoryAssistant.chat("我是秦始皇");
        System.out.println(res);
        String res1 = chatMemoryAssistant.chat("我是谁？");
        System.out.println(res1);
    }

    /**
     * 可分区的聊天记忆智能助理
     */
    @Autowired
    private PartitionChatMemoryAssistant partitionChatMemoryAssistant;

    /**
     * 测试langchain4j的对话记忆，测试结果：具有对话记忆，实现方式比较简单，使用可分区的ChatMemory的方式实现
     */
    @Test
    public void test5() {
        String res = partitionChatMemoryAssistant.chat("1", "我是秦始皇");
        System.out.println(res);
        String res1 = partitionChatMemoryAssistant.chat("1", "我是谁？");
        System.out.println(res1);
        String res2 = partitionChatMemoryAssistant.chat("2", "我是谁？");
        System.out.println(res2);
    }

    /**
     * 测试langchain4j的对话记忆，测试结果：具有对话记忆，实现方式比较简单，使用可分区的ChatMemory的方式实现，
     */
    @Autowired
    private MongoDbChatMemoryAssistant mongoDbChatMemoryAssistant;

    /**
     * 测试langchain4j的对话记忆，测试结果：具有对话记忆，实现方式比较简单，使用可分区的ChatMemory的方式实现，
     */
    @Test
    public void test6() {
        String res = mongoDbChatMemoryAssistant.chat("1", "我是秦始皇");
        System.out.println(res);
        String res1 = mongoDbChatMemoryAssistant.chat("1", "我是谁？");
        System.out.println(res1);
        String res2 = mongoDbChatMemoryAssistant.chat("2", "我是谁？");
        System.out.println(res2);
    }

    /**
     * 测试系统提示词，测试结果：具有系统提示词
     */
    @Test
    public void test7() {
        String res = partitionChatMemoryAssistant.chat("1", "我是谁");
        // 你这个问题问的，我咋个晓得你是哪个嘛！你自己晓不晓得你是谁呢？哈哈。
        System.out.println(res);
    }

    /**
     * 测试用户提示词，测试结果：具有系统提示词
     */
    @Test
    public void test8() {
        String res = mongoDbChatMemoryAssistant.chat4UserMessage("11", "你吃饭了吗？没吃一起，你请客", "小白", 18);
        // 哎呀，哥们儿，你这话说得我心花怒放啊！不过咱俩这关系，谁请客不都一样嘛，哈哈。今儿个2025年7月3号了，时间过得嗖嗖的，转眼间你就18啦，真是越长越精神了！
        // 说到吃饭，还真巧了，我这正琢磨着吃点啥呢。你说一起吧，那必须得去整点儿好的，咱们东北菜咋样？来盘锅包肉、再来点儿酸菜白肉血肠，想想都馋人啊！不过，说好了哈，今天哥请你，下回可得轮到你出手了，咱俩就这么愉快地决定了！
        // 走吧，别墨迹了，再晚好地方就让人占上了！
        System.out.println(res);
        res = mongoDbChatMemoryAssistant.chat4UserMessage("11", "你上次不是说下次你请客吗？", "小白", 18);
        // 哎呀，小白，你这记性可真好啊，不愧是我哥们儿！上次说下次我请客，那必须得算数啊。今儿个2025年7月3号了，咱们就去整点儿好吃的吧！
        // 不过，话说回来，咱俩这关系，谁请客不都一样嘛，哈哈。你说想吃啥？锅包肉、杀猪菜还是烧烤？反正今天哥请你，管够吃，管够喝，咱们好好搓一顿！
        // 走吧，别磨叽了，再晚好地方就让人占上了！
        System.out.println(res);
    }

    /**
     * 测试系统提示词，测试结果：具有系统提示词
     */
    @Test
    public void test9() {
        String res = mongoDbChatMemoryAssistant.chat4SystemMessage("10", "你吃饭了吗？没吃一起，你请客", "小白", 18);
        // 哎呀，兄弟伙，你这话说得我心头一暖哈。不过我今天已经吃过咯，你要不找其他几个哥们儿一起嘛？要是你硬要拉我下馆子，那我也不好推辞噻，毕竟咱们是铁哥们儿嘛！但说好了哦，这次我请了，下次该你请客哈，咱们换到吃嘛！
        System.out.println(res);
        res = mongoDbChatMemoryAssistant.chat4SystemMessage("10", "你上次不是说下次你请客吗？", "小白", 18);
        // 哎呀，兄弟，你这记性也太好了嘛！上次说下次我请客，那这次就算我的嘛！不过话说回来，你这个小算盘打得也太精了吧，哈哈。走嘛，咱们去整点好吃的，今天哥子豁出去了，请你吃顿好的！你想吃啥子？火锅、串串还是干锅？
        System.out.println(res);
    }

}
