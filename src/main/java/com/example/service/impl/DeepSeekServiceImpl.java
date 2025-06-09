package com.example.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSON;
import com.example.service.DeepSeekService;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeepSeekServiceImpl implements DeepSeekService {

    private static final Logger logger = LoggerFactory.getLogger(DeepSeekService.class);

    @Value("${deepseek.api-url}")
    private String apiUrl;

    @Value("${deepseek.api-key}")
    private String apiKey;

    private final OkHttpClient client = new OkHttpClient();
    @Override
    public String chat(String message) {
        // 获取当前用户的对话上下文
        List<Map<String, String>> context = getConversationContext();

        // 添加用户的新消息到上下文
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", message);
        context.add(userMessage);

        // 构建请求体
        MediaType JSON_TYPE = MediaType.parse("application/json");
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("messages", context);
        requestBody.put("model", "deepseek-chat");
        RequestBody body = RequestBody.create(JSON_TYPE, JSON.toJSONString(requestBody));

        // 构建请求
        Request request = new Request.Builder()
                .url(apiUrl)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .method("POST", body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseData = response.body().string();
                // 解析响应并提取 DeepSeek 的回复
                Map<String, Object> responseMap = JSON.parseObject(responseData, Map.class);
                List<Map<String, Object>> choices = (List<Map<String, Object>>) responseMap.get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map<String, Object> choice = choices.get(0);
                    Map<String, String> messageMap = (Map<String, String>) choice.get("message");
                    String reply = messageMap.get("content");

                    // 添加 DeepSeek 的回复到上下文
                    Map<String, String> assistantMessage = new HashMap<>();
                    assistantMessage.put("role", "assistant");
                    assistantMessage.put("content", reply);
                    context.add(assistantMessage);

                    // 更新对话上下文到 sa-token 会话中
                    saveConversationContext(context);

                    return reply;
                }
            }
        } catch (IOException e) {
            logger.error("与 DeepSeek API 通信时出错", e);
        }
        return "发生错误，请稍后再试。";
    }

    private List<Map<String, String>> getConversationContext() {
        List<Map<String, String>> context = StpUtil.getSession().get("conversationContext", new ArrayList<>());
        return context;
    }

    private void saveConversationContext(List<Map<String, String>> context) {
        StpUtil.getSession().set("conversationContext", context);
    }
}
