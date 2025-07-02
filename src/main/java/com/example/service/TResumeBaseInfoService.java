package com.example.service;

import com.example.entity.TResumeBaseInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.req.SaveResumeReq;

public interface TResumeBaseInfoService extends IService<TResumeBaseInfo>{


    void saveResume(SaveResumeReq saveResumeReq);
}
