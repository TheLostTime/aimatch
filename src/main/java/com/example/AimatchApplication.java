package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.example.mapper")
public class AimatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(AimatchApplication.class, args);
    }


    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}