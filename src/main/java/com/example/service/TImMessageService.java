package com.example.service;

import com.example.entity.TImMessage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TImMessageService extends IService<TImMessage>{


    List<TImMessage> chatSessionList(String positionId, String resumeId);
}
