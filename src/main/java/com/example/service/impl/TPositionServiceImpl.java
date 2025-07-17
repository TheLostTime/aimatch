package com.example.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.THrMarkResume;
import com.example.entity.THrPaidPermisionsUseDetail;
import com.example.entity.THrVip;
import com.example.entity.TPosition;
import com.example.exception.BusinessException;
import com.example.mapper.THrMapper;
import com.example.mapper.THrMarkResumeMapper;
import com.example.mapper.TPositionMapper;
import com.example.req.AuditPositionReq;
import com.example.req.ResumeListReq;
import com.example.resp.ResumeListResp;
import com.example.resp.TalentListResp;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.constant.Constants.*;

@Service
public class TPositionServiceImpl extends ServiceImpl<TPositionMapper, TPosition> implements TPositionService{

    @Autowired
    private THrMapper tHrMapper;

    @Autowired
    private THrVipService tHrVipService;

    @Autowired
    private THrMarkResumeService tHrMarkResumeService;

    @Autowired
    private THrMarkResumeMapper tHrMarkResumeMapper;

    @Autowired
    private THrPaidPermisionsUseDetailService tHrPaidPermisionsUseDetailService;


    @Override
    public List<TPosition> queryPositionList(String positionStatus) {
        // 查询所有岗位(自己的)
        return baseMapper.selectList( new LambdaQueryWrapper<TPosition>()
                .eq(TPosition::getPositionStatus, positionStatus)
                .eq(TPosition::getUserId, StpUtil.getLoginId().toString()));
    }

    @Override
    public void auditPosition(AuditPositionReq auditPositionReq) {
        String positionId = auditPositionReq.getPositionId();
        Integer status = auditPositionReq.getStatus();
        String reason = auditPositionReq.getReason();
        // 查询当前岗位
        TPosition tPosition = baseMapper.selectById(positionId);
        if (null == tPosition) {
            throw new BusinessException(10015,"岗位不存在");
        }
        if (tPosition.getPositionStatus() != POSITION_STATUS_AUDIT) {
            throw new BusinessException(10017,"只有待审核的岗位才能审核");
        }
        if (status == POSITION_STATUS_ONLINE) {
            // 审核通过
            baseMapper.updateById(TPosition.builder()
                    .positionId(positionId)
                    .positionStatus(POSITION_STATUS_ONLINE)
                    .updateTime(DateUtil.date())
                    .build());
        }
        if (status == POSITION_STATUS_REJECT) {
            // 审核不通过
            baseMapper.updateById(TPosition.builder()
                    .positionId(positionId)
                    .positionStatus(POSITION_STATUS_REJECT)
                    .updateTime(DateUtil.date())
                    .reason(reason)
                    .build());
            THrPaidPermisionsUseDetail tHrPaidPermisionsUseDetail = tHrPaidPermisionsUseDetailService
                    .getById(tPosition.getUserId());
            // 岗位累计额度+1
            tHrPaidPermisionsUseDetail.setUsedPositionNum(tHrPaidPermisionsUseDetail.getUsedPositionNum()+1);
            tHrPaidPermisionsUseDetailService.updateById(tHrPaidPermisionsUseDetail);
        }
    }

    @Override
    public List<ResumeListResp> getResumeList(ResumeListReq resumeListReq) {
        THrVip tHrVip = tHrVipService.getById(StpUtil.getLoginId().toString());
        if ("ai".equals(resumeListReq.getQueryType())
                && (null == tHrVip || !tHrVip.getVipType().equals(VIP_NORMAL)
                        || !tHrVip.getVipType().equals(VIP_HIGH) )) {
                throw new BusinessException(10020,"会员才能使用ai智能筛选");
        }
        // 查询人才列表
        resumeListReq.setHrId(StpUtil.getLoginId().toString());
        List<ResumeListResp> resumeListRespList = tHrMarkResumeMapper.getResumeList(resumeListReq);
        return resumeListRespList;
    }

    @Override
    public void markResume(String resumeId,String positionId,String status) {
        // 查询简历标记
        THrMarkResume tHrMarkResume = tHrMarkResumeService.getOne(new LambdaQueryWrapper<THrMarkResume>()
                .eq(THrMarkResume::getPositionId, positionId)
                .eq(THrMarkResume::getResumeId, resumeId));
        if (null == tHrMarkResume) {
            throw new BusinessException(10021,"该简历还未被收藏");
        }
        // 设置状态
        if (!status.equals(RESUME_STATUS_INTERVIEW) && !status.equals(RESUME_STATUS_NOT_SUIT)) {
            throw new BusinessException(10022,"状态错误");
        }
        tHrMarkResume.setResumeStatus(status);
        tHrMarkResumeService.updateById(tHrMarkResume);
    }

    @Override
    public List<TalentListResp> getTalentList(String positionId, String resumeStatus) {
        List<TalentListResp> talentListRespList = tHrMarkResumeMapper
                .getTalentList(positionId, resumeStatus, StpUtil.getLoginId().toString());
        return talentListRespList;
    }

    @Override
    public void checkSayHello() {
        // 查询用户使用权限
        THrPaidPermisionsUseDetail tHrPaidPermisionsUseDetail = tHrPaidPermisionsUseDetailService
                .getById(StpUtil.getLoginId().toString());
        if (null == tHrPaidPermisionsUseDetail) {
            throw new BusinessException(10023,"未开通vip权限，没有使用额度");
        }
        if (tHrPaidPermisionsUseDetail.getUsedSayHello() <= 0) {
            throw new BusinessException(10024,"已使用打招呼数量已达到上限");
        }
    }

    @Override
    public void checkDownload() {
        // 查询用户使用权限
        THrPaidPermisionsUseDetail tHrPaidPermisionsUseDetail = tHrPaidPermisionsUseDetailService
                .getById(StpUtil.getLoginId().toString());
        if (null == tHrPaidPermisionsUseDetail) {
            throw new BusinessException(10023,"未开通vip权限，没有使用额度");
        }
        if (tHrPaidPermisionsUseDetail.getUsedDownloadNum() <= 0) {
            throw new BusinessException(10025,"已使用下载数量已达到上限");
        }
    }
}
