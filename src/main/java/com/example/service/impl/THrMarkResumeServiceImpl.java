package com.example.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.TExam;
import com.example.entity.THrMarkResume;
import com.example.entity.TUser;
import com.example.mapper.THrMarkResumeMapper;
import com.example.resp.ChatSessionResp;
import com.example.service.TExamService;
import com.example.service.THrMarkResumeService;
import com.example.service.TUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class THrMarkResumeServiceImpl extends ServiceImpl<THrMarkResumeMapper, THrMarkResume> implements THrMarkResumeService{

    @Autowired
    private TUserService tUserService;

    @Autowired
    private TExamService tExamService;

    @Override
    public List<ChatSessionResp> chatSessionList(String positionId, String hrName, String resumeStatus) {
        String userId = StpUtil.getLoginIdAsString();
        // 获取用户信息
        TUser tUser = tUserService.getById(userId);
        List<ChatSessionResp> chatSessionRespList = this.getBaseMapper()
                .chatSessionList(positionId, hrName, resumeStatus,userId,tUser.getUserType());
        return chatSessionRespList;
    }

    @Override
    public void updateTHrMarkResume(THrMarkResume tHrMarkResume) {
        this.getBaseMapper().updateTHrMarkResume(tHrMarkResume);
    }

    @Override
    public void handePaper(String positionId, String resumeId, String examId) {
        // 查询当前t_mark_resume
        THrMarkResume tHrMarkResume = this.getOne(new LambdaQueryWrapper<THrMarkResume>()
                .eq(THrMarkResume::getResumeId, resumeId)
                .eq(THrMarkResume::getPositionId, positionId));
        if (null == tHrMarkResume) {
            log.error("未找到该简历");
            return;
        }

        // 如果卷子不存在则保存卷子
        TExam tExam = tExamService.getById(examId);
        if (null == tExam) {
            SaSession session = StpUtil.getSession();
            List<Map<String, String>> message = session.get(examId, new ArrayList<>());
            String loginId = StpUtil.getLoginIdAsString();
            // 保存答题的数据,从第三条数据开始存储
            for (int i = 1; i < message.size(); i++) {
                 tExam = TExam.builder()
                        .employeeUserId(loginId)
                        .hrUserId(tHrMarkResume.getHrUserId())
                        .content(message.get(i).get("content"))
                        .queIndex(i)
                        .positionId(positionId)
                        .resumeId(resumeId)
                        .createTime(DateUtil.date())
                        .build();
                tExamService.save(tExam);
            }
        }

        if (StringUtils.isEmpty(tHrMarkResume.getTestScores())) {
            // 设置一个30-60的随机分数
            tHrMarkResume.setTestScores(String.valueOf(Math.round(Math.random() * 30 + 30)));
            this.updateTHrMarkResume(tHrMarkResume);
        } else {
            // 不做任何事情
        }
    }
}
