package com.learn.controller;

import com.learn.assistant.XiaoZhiChatAgent;
import com.learn.vo.XiaoZhiChatVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @ClassName -> XiaoZhiAgentController
 * @Description
 * @Author soso
 * @Date 2025/7/3 17:33 星期四
 * @Version 1.0
 */
@Tag(name = "小智医疗助手", description = "小智医疗助手相关接口")
@RestController
@RequestMapping("/xiaozhi")
public class XiaoZhiAgentController {

    /**
     * 小智医疗助手
     */
    @Autowired
    private XiaoZhiChatAgent xiaoZhiChatAgent;

    /**
     * 小智医疗助手聊天接口
     * @param xiaoZhiChatVO 小智医疗助手聊天VO
     * @return 小智医疗助手聊天响应
     */
    @Operation(summary = "小智医疗助手聊天接口", description = "小智医疗助手聊天接口")
    @PostMapping("/chat")
    public String chat(@RequestBody
                           @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "小智聊天助手VO", required = true)
                           XiaoZhiChatVO xiaoZhiChatVO) {
        return xiaoZhiChatAgent.chat(xiaoZhiChatVO.getMemoryId(), xiaoZhiChatVO.getUserMessage());
    }

    /**
     * 小智医疗助手聊天接口，流式输出
     * @param xiaoZhiChatVO 小智医疗助手聊天VO
     * @return 小智医疗助手聊天响应
     */
    @Operation(summary = "小智医疗助手聊天接口", description = "小智医疗助手聊天接口")
    @PostMapping("/chat4Flux")
    public Flux<String> chat4Flux(@RequestBody
                           @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "小智聊天助手VO", required = true)
                           XiaoZhiChatVO xiaoZhiChatVO) {
        return xiaoZhiChatAgent.chat4Flux(xiaoZhiChatVO.getMemoryId(), xiaoZhiChatVO.getUserMessage());
    }

}
