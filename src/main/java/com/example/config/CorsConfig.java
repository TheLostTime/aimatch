package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 允许的前端域名，* 表示允许所有（生产环境不建议用 *，要指定具体域名）
        config.setAllowedOriginPatterns(Arrays.asList("*")); // 使用patterns替代origins
        config.setAllowCredentials(true); // 允许携带凭证
        config.addAllowedMethod("*"); // 允许所有 HTTP 方法（GET、POST 等）
        config.addAllowedHeader("*"); // 允许所有请求头

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // 对所有接口生效

        return new CorsFilter(source);
    }
}
