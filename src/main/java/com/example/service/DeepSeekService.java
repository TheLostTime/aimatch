package com.example.service;

import reactor.core.publisher.Flux;

public interface DeepSeekService {
    String chat(String message,Boolean needSaveContext,String contextName);

    Flux<String> chatFlux(String content);
}
