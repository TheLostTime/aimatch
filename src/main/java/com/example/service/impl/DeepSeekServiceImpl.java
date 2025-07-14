package com.example.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSON;
import com.example.service.DeepSeekService;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class DeepSeekServiceImpl implements DeepSeekService {

    private static final Logger logger = LoggerFactory.getLogger(DeepSeekService.class);

    @Value("${deepseek.api-url}")
    private String apiUrl;

    @Value("${deepseek.api-key}")
    private String apiKey;

    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS) // 连接超时 10 秒
            .readTimeout(60, TimeUnit.SECONDS)    // 读取超时 30 秒
            .writeTimeout(30, TimeUnit.SECONDS)   // 写入超时 30 秒
            //.callTimeout(60, TimeUnit.SECONDS)    // 整个请求超时 60 秒（OkHttp 4.0+）
            .build();
    @Override
    public String chat(String message,Boolean needSaveContext,String contextName) {
        // 获取当前用户的对话上下文
        List<Map<String, String>> context = new ArrayList<>();
        if (needSaveContext) {
            context = getConversationContext(contextName);
        }
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

                    if (needSaveContext) {
                        // 更新对话上下文到 sa-token 会话中
                        saveConversationContext(context, contextName);
                    }
                    return reply;
                }
            }
        } catch (IOException e) {
            logger.error("与 DeepSeek API 通信时出错", e);
        }
        return "发生错误，请稍后再试。";
    }

    private List<Map<String, String>> getConversationContext(String contextName) {
        return StpUtil.getSession().get(contextName, new ArrayList<>());
    }

    private void saveConversationContext(List<Map<String, String>> context,String contextName) {
        StpUtil.getSession().set(contextName, context);
    }


    private final WebClient webClient;
    private final Map<String, String> contextStore = new HashMap<>();

    public DeepSeekServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    @Override
    public Flux<String> chatFlux(String content) {

        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", content);
        requestBody.put("model", "deepseek-chat");
        requestBody.put("messages", new Object[]{
                //Map.of("role", "user", "content", content)
                userMessage
        });
        requestBody.put("stream", true);

        // 调用DeepSeek API并处理流式响应
        return webClient.post()
                .uri(apiUrl)
                .headers(h -> h.setBearerAuth(apiKey))
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToFlux(String.class)
                .map(chunk -> {
                    // 解析SSE格式的响应
//                    if (chunk.startsWith("data: ")) {
//                        chunk = chunk.substring(6);
//                    }
//                    if (chunk.equals("[DONE]")) {
//                        // 更新上下文存储
//                        contextStore.put(contextId, fullPrompt + "\n\n" + chunk);
//                        return "";
//                    }
                    return chunk;
                });
    }
}
