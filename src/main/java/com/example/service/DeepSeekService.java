package com.example.service;

import cn.dev33.satoken.session.SaSession;
import com.example.req.ChatExamReq;
import reactor.core.publisher.Flux;

public interface DeepSeekService {
    String chat(String message,Boolean needSaveContext,String contextName);

    Flux<String> chatFlux(String content, Boolean needSaveContext, String contextName, SaSession session);
    Flux<String> chatFluxExam( Boolean needSaveContext,  SaSession session, ChatExamReq chatExamReq,String loginId,String hrUserId);
}
