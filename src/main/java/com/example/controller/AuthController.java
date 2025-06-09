package com.example.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.example.entity.ResponseResult;
import com.example.entity.User;
import com.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseResult<?> register(@RequestBody User user) {
        logger.info("接收注册请求: {}", user.getUsername());
        userService.registerUser(user);
        return ResponseResult.success();
    }

    @PostMapping("/login")
    public ResponseResult<?> login(@RequestBody User user) {
        logger.info("接收登录请求: {}", user.getUsername());
        String token = userService.login(user);
        return ResponseResult.success(token);
    }



    @PostMapping("/logout")
    public ResponseResult<?> logout() {
        logger.info("用户登出: {}", StpUtil.getLoginId());
        StpUtil.logout();
        return ResponseResult.success();
    }


    @GetMapping("/checkToken")
    public Map<String,Object> checkToken(@RequestParam String username, @RequestParam String token) {
        // 验证 Token
        Object loginId = StpUtil.getLoginIdByToken(token);
        boolean valid = loginId != null;
        Map<String,Object> retMap= new HashMap<>();
        retMap.put("valid", valid);
        retMap.put("userId", loginId);
        // 返回用户信息
        return retMap;

    }
}    