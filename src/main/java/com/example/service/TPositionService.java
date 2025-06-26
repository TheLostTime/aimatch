package com.example.service;

import com.example.entity.TPosition;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TPositionService extends IService<TPosition>{

    List<TPosition> queryPositionList();

    void auditPosition(String positionId, Integer status, String reason);
}
