package com.example.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor())
                // 拦截所有路径
                .addPathPatterns("/**")
                // 放行 OPTIONS 请求和登录等无需校验的接口
                .excludePathPatterns("/**/OPTIONS");
    }
}

