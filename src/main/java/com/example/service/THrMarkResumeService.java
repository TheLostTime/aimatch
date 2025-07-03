package com.example.service;

import com.example.entity.THrMarkResume;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.resp.ChatSessionResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface THrMarkResumeService extends IService<THrMarkResume> {

    List<ChatSessionResp> chatSessionList(String positionId, String name,String resumeStatus);
}
