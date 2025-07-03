package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.example.entity.ResponseResult;
import com.example.entity.TUser;
import com.example.service.TUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "用户管理API", tags = {"用户管理"})
@RestController
@RequestMapping("/v1")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private TUserService userService;


    @ApiOperation(value = "注册", notes = "", httpMethod = "POST")
    @PostMapping("/register")
    public ResponseResult<?> register(@RequestBody TUser user) {
        logger.info("接收注册请求: {}", user.getAccount());
        userService.registerUser(user);
        return ResponseResult.success();
    }

    @SaCheckLogin
    @PostMapping("/updateAvatar")
    @ApiOperation(value = "更新用户头像", notes = "", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "file", name = "avatar", value = "", required = false),
    })
    public ResponseResult<?> updateAvatar(@RequestPart @RequestParam("avatarFile") MultipartFile avatarFile) {
        userService.updateAvatar(avatarFile);
        return ResponseResult.success();
    }

    @ApiOperation(value = "登录", notes = "", httpMethod = "POST")
    @PostMapping("/login")
    public ResponseResult<?> login(@RequestBody TUser user) {
        logger.info("接收登录请求: {}", user.getAccount());
        String token = userService.login(user);
        return ResponseResult.success(token);
    }

    @ApiOperation(value = "登出", notes = "", httpMethod = "POST")
    @PostMapping("/logout")
    public ResponseResult<?> logout() {
        StpUtil.logout();
        return ResponseResult.success();
    }

    @ApiOperation(value = "判断当前用户是否登录", notes = "", httpMethod = "GET")
    @GetMapping("/isLogin")
    public ResponseResult<?> isLogin() {
        if (StpUtil.isLogin()) {
            SaSession saSession = StpUtil.getSession();
            TUser userInfo = (TUser) saSession.get("userInfo");
            userInfo.setPassword("");
            return ResponseResult.success(userInfo);
        } else {
            return ResponseResult.error(201,"未登录");
        }
    }


}    