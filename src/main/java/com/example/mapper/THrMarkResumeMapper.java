package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.THrMarkResume;
import com.example.req.ResumeListReq;
import com.example.resp.ChatSessionResp;
import com.example.resp.ResumeListResp;
import com.example.resp.TalentListResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface THrMarkResumeMapper extends BaseMapper<THrMarkResume> {
    List<ChatSessionResp> chatSessionList(@Param("positionId") String positionId,
                                          @Param("name") String name,
                                          @Param("resumeStatus") String resumeStatus,
                                          @Param("userId") String userId,
                                          @Param("userType") String userType);

    List<ResumeListResp> getResumeList(ResumeListReq resumeListReq);

    List<TalentListResp> getTalentList(@Param("positionId") String positionId,
                                       @Param("resumeStatus") String resumeStatus,
                                       @Param("userId") String userId);
}