package com.learn.vo;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName -> XiaoZhiChatVO
 * @Description
 * @Author soso
 * @Date 2025/7/3 17:35 星期四
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class XiaoZhiChatVO {
    /**
     * 聊天记忆id
     */
    @Parameter(description = "聊天记忆id", required = true)
    private String memoryId;

    /**
     * 用户消息
     */
    @Parameter(description = "用户消息", required = true)
    private String userMessage;
}
