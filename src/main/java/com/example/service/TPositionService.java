package com.example.service;

import com.example.entity.TPosition;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.req.AuditPositionReq;
import com.example.req.ResumeListReq;
import com.example.resp.*;

import java.util.List;

public interface TPositionService extends IService<TPosition>{

    List<PositionListResp> queryPositionList(String positionStatus);

    void auditPosition(AuditPositionReq auditPositionReq);

    List<ResumeListResp> getResumeList(ResumeListReq resumeListReq);

    void markResume(String resumeId,String positionId,String status);

    List<TalentListResp> getTalentList(String positionId, String resumeStatus);

    void checkSayHello();

    void checkDownload();

    void cancelMark(String resumeId, String positionId);

    QueryPositionManageByPageResp queryPositionManageList(String positionStatus, Integer pageNum, Integer pageSize);

    List<RecommendResumeResp> recommendResumeList(String positionId,Integer size);

    RcAndPositionResp getVipPackage();
}
