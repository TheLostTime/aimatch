package com.example.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.entity.TUser;
import com.example.resp.ChatSessionResp;
import com.example.resp.ResumeDetailResp;
import com.example.service.TUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.THrMarkResume;
import com.example.mapper.THrMarkResumeMapper;
import com.example.service.THrMarkResumeService;

import static com.example.constant.Constants.*;

@Service
public class THrMarkResumeServiceImpl extends ServiceImpl<THrMarkResumeMapper, THrMarkResume> implements THrMarkResumeService{

    @Autowired
    private TUserService tUserService;

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
    public void handePaper(String positionId, String resumeId) {
        // 查询当前t_mark_resume
        THrMarkResume tHrMarkResume = this.getOne(new LambdaQueryWrapper<THrMarkResume>()
                .eq(THrMarkResume::getResumeId, resumeId)
                .eq(THrMarkResume::getPositionId, positionId));
        if (null == tHrMarkResume) {
            log.error("未找到该简历");
            return;
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
