//package com.example.config;
//
//import cn.dev33.satoken.context.SaHolder;
//import cn.dev33.satoken.context.SaTokenContext;
//import cn.dev33.satoken.interceptor.SaInterceptor;
//import cn.dev33.satoken.spring.SaTokenContextForSpring;
//import cn.dev33.satoken.stp.StpLogic;
//import cn.dev33.satoken.stp.StpUtil;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Configuration
//public class SaTokenConfigure implements WebMvcConfigurer {
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
////        // 添加 Token 解析拦截器
////        registry.addInterceptor(new HandlerInterceptor() {
////            @Override
////            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
////                String authHeader = request.getHeader("Authorization");
////                if (authHeader != null && authHeader.startsWith("Bearer ")) {
////                    String token = authHeader.substring(7);
////                    StpUtil.setTokenValue(token);
////                }
////                return true;
////            }
////        }).addPathPatterns("/**");
////
////        // 注册 Sa-Token 登录校验拦截器
////        registry.addInterceptor(new SaInterceptor(handler -> StpUtil.checkLogin()))
////                .addPathPatterns("/api/**")
////                .excludePathPatterns("/api/auth/**");
//    }
//}