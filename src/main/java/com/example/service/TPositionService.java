package com.example.service;

import com.example.entity.TPosition;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.req.ResumeListReq;
import com.example.resp.ResumeListResp;
import com.example.resp.TalentListResp;

import java.util.List;

public interface TPositionService extends IService<TPosition>{

    List<TPosition> queryPositionList(String positionStatus);

    void auditPosition(String positionId, Integer status, String reason);

    List<ResumeListResp> getResumeList(ResumeListReq resumeListReq);

    void markResume(String resumeId,String positionId,String status);

    List<TalentListResp> getTalentList(String positionId, String resumeStatus);
}
