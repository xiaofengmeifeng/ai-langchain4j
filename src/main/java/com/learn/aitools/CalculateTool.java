package com.learn.aitools;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

/**
 * @ClassName -> CalculateTool
 * @Description 计算工具
 * @Author soso
 * @Date 2025/7/3 18:09 星期四
 * @Version 1.0
 */
@Component
public class CalculateTool {

    /**
     * 用于计算两个整数的和
     * @param a 第一个数
     * @param b 第二个数
     * @return 和
     */
    @Tool(name = "sum", value = "用于计算两个整数的和")
    public Integer sum(Integer a, Integer b) {
        System.out.println("计算结果为：" + (a + b));
        return a + b;
    }

    /**
     * 用于计算两个整数的商
     * @param a 第一个数
     * @param b 第二个数
     * @return 差
     */
    @Tool(name = "divide", value = "用于计算两个整数的商")
    public Double divide(Double a, Double b) {
        System.out.println("计算结果为：" + (a / b));
        return a / b;
    }

}
