package com.example.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.entity.THrMarkResume;
import com.example.req.ChatExamReq;
import com.example.service.DeepSeekService;
import com.example.service.THrMarkResumeService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class DeepSeekServiceImpl implements DeepSeekService {

    private static final Logger logger = LoggerFactory.getLogger(DeepSeekService.class);

    @Value("${deepseek.api-url}")
    private String apiUrl;

    @Value("${deepseek.api-key}")
    private String apiKey;

    @Autowired
    private THrMarkResumeService tHrMarkResumeService;

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
    public Flux<String> chatFlux(String content, Boolean needSaveContext, String contextName, SaSession session) {
        // 获取当前用户的对话上下文
        List<Map<String, String>> context = new ArrayList<>();
        if (needSaveContext) {
            context = session.get(contextName, new ArrayList<>());
        }
        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", content);
        requestBody.put("model", "deepseek-chat");
        context.add(userMessage);
        requestBody.put("messages", context);
        requestBody.put("stream", true);

        // 存储完整的助手回复
        StringBuilder fullAssistantResponse = new StringBuilder();
        // 调用DeepSeek API并处理流式响应
        List<Map<String, String>> finalContext = context;
        return webClient.post()
                .uri(apiUrl)
                .headers(h -> h.setBearerAuth(apiKey))
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToFlux(String.class)
                .doOnNext(chunk -> {
                    // 处理流式响应块
                    if (chunk != null && !chunk.equals("[DONE]")) {
                        // 解析数据块并提取内容
                        String contentChunk = extractContentFromChunk(chunk);
                        if (contentChunk != null) {
                            fullAssistantResponse.append(contentChunk);
                        }
                    }
                })
                .doOnComplete(() -> {
                    // 当流完成时保存完整上下文
                    if (needSaveContext && contextName != null) {
                        // 添加助手回复到上下文
                        if (fullAssistantResponse.length() > 0) {
                            Map<String, String> assistantMessage = new HashMap<>();
                            assistantMessage.put("role", "assistant");
                            assistantMessage.put("content", fullAssistantResponse.toString());
                            finalContext.add(assistantMessage);
                            // 保存完整上下文
                            log.info("保存完整上下文：{}", fullAssistantResponse);
                            session.set(contextName, finalContext);
                        }
                    }
                })
                .onErrorResume(e -> {
                    log.error("Error while processing chat stream: {}", e.getMessage(), e);
                    return Flux.empty();
                });
    }

    @Override
    public Flux<String> chatFluxExam(Boolean needSaveContext, SaSession session, ChatExamReq chatExamReq,String loginId) {
        // 获取当前用户的对话上下文
        List<Map<String, String>> context = new ArrayList<>();
        String contextName = chatExamReq.getExamId();
        if (needSaveContext) {
            context = session.get(contextName, new ArrayList<>());
        }
        // 构建请求体
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", chatExamReq.getContent());
        requestBody.put("model", "deepseek-chat");
        context.add(userMessage);
        requestBody.put("messages", context);
        requestBody.put("stream", true);

        // 存储完整的助手回复
        StringBuilder fullAssistantResponse = new StringBuilder();
        // 调用DeepSeek API并处理流式响应
        List<Map<String, String>> finalContext = context;
        return webClient.post()
                .uri(apiUrl)
                .headers(h -> h.setBearerAuth(apiKey))
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToFlux(String.class)
                .doOnNext(chunk -> {
                    // 处理流式响应块
                    if (chunk != null && !chunk.equals("[DONE]")) {
                        // 解析数据块并提取内容
                        String contentChunk = extractContentFromChunk(chunk);
                        if (contentChunk != null) {
                            fullAssistantResponse.append(contentChunk);
                        }
                    }
                })
                .doOnComplete(() -> {
                    // 当流完成时保存完整上下文
                    if (needSaveContext && contextName != null) {
                        // 添加助手回复到上下文
                        if (fullAssistantResponse.length() > 0) {
                            Map<String, String> assistantMessage = new HashMap<>();
                            assistantMessage.put("role", "assistant");
                            assistantMessage.put("content", fullAssistantResponse.toString());
                            finalContext.add(assistantMessage);
                            // 保存完整上下文
                            log.info("保存完整上下文：{}", fullAssistantResponse);
                            session.set(contextName, finalContext);
                            // 判断是否是最后一道题结果
                            List<Map<String, String>> message = session.get(contextName, new ArrayList<>());
                            if (message.size() == (chatExamReq.getExamNum()+ 1) * 2 ) {
                                String lastMessage = message.get(message.size() - 1).get("content");
                                if (StringUtils.isNotEmpty(lastMessage)) {
                                    int score = extractScore(lastMessage);
                                    if (score != -1) {
                                        // 查询t_hr_mark_resume
                                        THrMarkResume tHrMarkResume = tHrMarkResumeService.getOne(new LambdaQueryWrapper<THrMarkResume>()
                                                .eq(THrMarkResume::getEmployeeUserId, loginId)
                                                .eq(THrMarkResume::getPositionId, chatExamReq.getPositionId()));
                                        tHrMarkResume.setTestScores(score + "");
                                        tHrMarkResume.setUpdateTime(DateUtil.date());
                                        tHrMarkResumeService.updateTHrMarkResume(tHrMarkResume);
                                    }
                                }
                            }
                        }
                    }
                })
                .onErrorResume(e -> {
                    log.error("Error while processing chat stream: {}", e.getMessage(), e);
                    return Flux.empty();
                });
    }

    private String extractContentFromChunk(String chunk) {
        // 处理SSE格式的数据: data: {"choices":[{"delta":{"content":"chunk"}}]}
        try {
            // 使用Jackson解析JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(chunk);

            // 提取 content 字段
            JsonNode choicesNode = root.get("choices");
            if (choicesNode != null && choicesNode.isArray() && choicesNode.size() > 0) {
                JsonNode deltaNode = choicesNode.get(0).get("delta");
                if (deltaNode != null) {
                    JsonNode contentNode = deltaNode.get("content");
                    if (contentNode != null && contentNode.isTextual()) {
                        return contentNode.asText();
                    }
                }
            }
        } catch (Exception e) {
            log.warn("Failed to parse content chunk: {}", e.getMessage());
        }
        return null;
    }

    public int extractScore(String text) {
        // 处理旧格式："最终得分：XX"
        String oldPrefix = "最终得分：";
        int oldStartIndex = text.indexOf(oldPrefix);

        if (oldStartIndex != -1) {
            oldStartIndex += oldPrefix.length();
            int oldEndIndex = oldStartIndex;

            while (oldEndIndex < text.length() && Character.isDigit(text.charAt(oldEndIndex))) {
                oldEndIndex++;
            }

            if (oldStartIndex != oldEndIndex) {
                try {
                    return Integer.parseInt(text.substring(oldStartIndex, oldEndIndex));
                } catch (NumberFormatException e) {
                    // 继续检查新格式
                }
            }
        }

        // 处理新格式："总分：XX/100"
        String newPrefix = "总分：";
        int newStartIndex = text.indexOf(newPrefix);

        if (newStartIndex != -1) {
            newStartIndex += newPrefix.length();
            int newEndIndex = newStartIndex;

            while (newEndIndex < text.length() &&
                    (Character.isDigit(text.charAt(newEndIndex)) || text.charAt(newEndIndex) == '/')) {
                newEndIndex++;
            }

            String scorePart = text.substring(newStartIndex, newEndIndex);
            // 提取斜杠前的数字部分
            String[] parts = scorePart.split("/");
            if (parts.length > 0) {
                try {
                    return Integer.parseInt(parts[0].trim());
                } catch (NumberFormatException e) {
                    return -1;
                }
            }
        }

        // 处理新格式2："得分：XX / 100"（支持数字与斜杠间的空格）
        String newPrefix2 = "得分：";
        int newStartIndex2 = text.indexOf(newPrefix2);

        if (newStartIndex2 != -1) {
            newStartIndex2 += newPrefix2.length();
            int newEndIndex2 = newStartIndex2;

            // 允许数字、空格和斜杠
            while (newEndIndex2 < text.length() &&
                    (Character.isDigit(text.charAt(newEndIndex2)) ||
                            text.charAt(newEndIndex2) == '/' ||
                            Character.isWhitespace(text.charAt(newEndIndex2)))) {
                newEndIndex2++;
            }

            String scorePart2 = text.substring(newStartIndex2, newEndIndex2);
            // 提取斜杠前的数字部分，先移除所有空格
            String cleaned = scorePart2.replaceAll("\\s+", "");
            String[] parts2 = cleaned.split("/");
            if (parts2.length > 0) {
                try {
                    return Integer.parseInt(parts2[0].trim());
                } catch (NumberFormatException e) {
                    return -1;
                }
            }
        }
        return -1;
    }
}
