package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.example.entity.ResponseResult;
import com.example.service.DeepSeekService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(value = "测试", tags = {"测试"})
@RestController
@RequestMapping("/api")
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private DeepSeekService deepSeekService;

    @SaCheckLogin
    @GetMapping("/hello")
    public ResponseResult<?> hello() {
        logger.info("访问受保护接口: {}", StpUtil.getLoginId());
        Map<String, Object> data = new HashMap<>();
        data.put("userId", StpUtil.getLoginId());
        return ResponseResult.success(data);
    }

    @SaCheckLogin
    @PostMapping("/chat")
    public ResponseResult<String> chat(@RequestBody Map<String, String> request) {
        String message = request.get("message");
        if (message == null || message.isEmpty()) {
            return ResponseResult.error("消息不能为空");
        }
        String reply = deepSeekService.chat(message);
        return ResponseResult.success(reply);
    }


}    