package com.learn.demo;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.community.model.dashscope.WanxImageModel;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.output.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import java.time.Duration;

/**
 * @ClassName -> Langchain4jDemoTest
 * @Description
 * @Author soso
 * @Date 2025/7/1 15:47 星期二
 * @Version 1.0
 */
@SpringBootTest
public class Langchain4jDemoTest {

    @Test
    public void test(){
        OpenAiChatModel model = OpenAiChatModel.builder()
                .baseUrl("http://langchain4j.dev/demo/openai/v1")
                .apiKey("demo")
                .modelName("gpt-4o-mini")
                .build();
        String res = model.chat("你是谁呀？");
        System.out.println(res);
    }

    /**
     * 注入springboot自动配置的langchain4j
     */
    @Autowired
    @Qualifier("openAiChatModel")
    private OpenAiChatModel openAiChatModel;

    /**
     * 测试springboot自动配置langchain4j
     * 结论：
     * 1. 配置文件中需要配置openai的apiKey和baseUrl
     * 2. 配置文件中可以配置modelName，默认是gpt-3.5-turbo
     */
    @Test
    public void springbootTest(){
        String res = openAiChatModel.chat("你是谁？");
        System.out.println(res);
    }

    /**
     * 测试本地部署的ollama
     */
    @Test
    public void ollamaTest(){
        OllamaChatModel ollamaChatModel = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("qwen3:1.7b")
                .responseFormat(ResponseFormat.JSON)
                .temperature(0.8)
                .timeout(Duration.ofSeconds(60))
                .build();
        String res = ollamaChatModel.chat("你是谁？");
        System.out.println(res);
    }


    /**
     * 注入springboot自动配置的ollama chatModel
     */
    @Autowired
    @Qualifier("ollamaChatModel")
    private ChatModel ollamaChatModel;

    /**
     * 测试springboot自动配置ollama chatModel
     */
    @Test
    public void ollamaSpringbootTest(){
        String res = ollamaChatModel.chat("你是谁？");
        System.out.println(res);
    }



    /**
     * 测试通义Qwen chatModel
     */
    @Test
    public void qwenChatModelTest(){
        ChatModel qwenChatModel = QwenChatModel.builder()
                .apiKey("sk-e1ad181f5a0e44b2b0ae93fd7809af7b")
                .modelName("qwen-max")
                .build();
        String res = qwenChatModel.chat("你是谁？");
        System.out.println(res);
    }

    /**
     * 注入springboot自动配置的通义Qwen chatModel
     */
    @Autowired
    @Qualifier("qwenChatModel")
    private ChatModel qwenChatModel;
    /**
     * 测试springboot自动配置通义Qwen chatModel
     */
    @Test
    public void qwenChatModelSpringbootTest(){
        String res = qwenChatModel.chat("你是谁？");
        System.out.println(res);
    }

    @Value("${langchain4j.community.dashscope.chat-model.api-key}")
    private String aLiApiKey;
    /**
     * 测试通义万象文生图模型
     */
    @Test
    public void wanxModelTest() {
        WanxImageModel wanxImageModel = WanxImageModel.builder()
                .modelName("wanx2.1-t2i-turbo")
                .apiKey(aLiApiKey)
                .build();
        Response<Image> response = wanxImageModel.generate("清纯可人的少女，身着简约白色连衣裙，搭配细腻透肤的黑色丝袜，展现出既纯洁又略带性感的魅力。她拥有一头柔顺光泽的黑长直发，自然垂落在肩上，几缕碎发轻轻拂过脸颊，增添了几分不经意的柔美。眼神清澈明亮，嘴角挂着淡淡的微笑，仿佛春日里的一缕温暖阳光，温柔地洒落在心间。需要全身照。");
        System.out.println(response.content().url());
    }
}
