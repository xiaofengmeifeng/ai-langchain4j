package com.learn.demo;

import com.learn.assistant.PartitionChatMemoryAssistant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @ClassName -> FunctionCallTest
 * @Description
 * @Author soso
 * @Date 2025/7/3 18:13 星期四
 * @Version 1.0
 */
@SpringBootTest
public class FunctionCallTest {

    /**
     * 智能助理
     */
    @Autowired
    private PartitionChatMemoryAssistant partitionChatMemoryAssistant;

    /**
     * 测试函数调用
     */
    @Test
    public void calculateTest() {
        String res = partitionChatMemoryAssistant.chat("99", "请计算72891与2718的和，再计算78912674816与421821.435621的商");
        System.out.println(res);
    }

}
