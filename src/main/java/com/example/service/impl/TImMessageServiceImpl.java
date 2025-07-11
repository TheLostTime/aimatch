package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import kotlin.jvm.internal.Lambda;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.TImMessageMapper;
import com.example.entity.TImMessage;
import com.example.service.TImMessageService;
@Service
public class TImMessageServiceImpl extends ServiceImpl<TImMessageMapper, TImMessage> implements TImMessageService{

    @Override
    public List<TImMessage> chatSessionList(String positionId, String resumeId) {
        // 根据positionId和resumeId查询聊天记录
        List<TImMessage> list = this.list(new LambdaQueryWrapper<TImMessage>()
                .eq(TImMessage::getPositionId, positionId)
                .eq(TImMessage::getResumeId, resumeId)
                .orderByAsc(TImMessage::getMessageIndex));
        return list;
    }
}
