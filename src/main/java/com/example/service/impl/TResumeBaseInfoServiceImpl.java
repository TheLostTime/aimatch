package com.example.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.*;
import com.example.exception.BusinessException;
import com.example.req.SaveResumeReq;
import com.example.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.TResumeBaseInfoMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class TResumeBaseInfoServiceImpl extends ServiceImpl<TResumeBaseInfoMapper, TResumeBaseInfo> implements TResumeBaseInfoService{

    @Autowired
    private TResumeWorkService tResumeWorkService;

    @Autowired
    private TResumeEducationService tResumeEducationService;

    @Autowired
    private TResumeProjectService tResumeProjectService;

    @Autowired
    private TResumeInterestJobService tResumeInterestJobService;

    @Override
    @Transactional
    public void saveResume(SaveResumeReq saveResumeReq) {
        // 获取用户ID
        String userId = StpUtil.getLoginId().toString();
        if (StringUtils.isEmpty(saveResumeReq.getResumeId())) {
            // 查询当前用户有没有简历
            List<TResumeBaseInfo> listBaseInfo = this.list(new LambdaQueryWrapper<TResumeBaseInfo>()
                    .eq(TResumeBaseInfo::getUserId, userId));
            if (!CollectionUtils.isEmpty(listBaseInfo)) {
                throw new BusinessException(10021, "当前用户已经保存过简历");
            }
            // 保存基本信息
            TResumeBaseInfo tResumeBaseInfo = saveResumeReq.getTResumeBaseInfo();
            List<TResumeWork> tResumeWork = saveResumeReq.getTResumeWork();
            List<TResumeEducation> tResumeEducation = saveResumeReq.getResumeEducation();
            List<TResumeProject> tResumeProject = saveResumeReq.getTResumeProject();
            List<TResumeInterestJob> tResumeInterestJob = saveResumeReq.getTResumeInterestJob();
            if (null == tResumeBaseInfo){
                throw new BusinessException(10022, "请保存基础信息");
            }
            tResumeBaseInfo.setUserId(userId);
            this.save(tResumeBaseInfo);
            if (!CollectionUtils.isEmpty(tResumeWork)) {
                tResumeWork.forEach(item -> item.setResumeId(tResumeBaseInfo.getResumeId()));
                tResumeWorkService.saveBatch(tResumeWork);
            }
            if (!CollectionUtils.isEmpty(tResumeEducation)) {
                tResumeEducation.forEach(item -> item.setResumeId(tResumeBaseInfo.getResumeId()));
                tResumeEducationService.saveBatch(tResumeEducation);
            }
            if (!CollectionUtils.isEmpty(tResumeProject)) {
                tResumeProject.forEach(item -> item.setResumeId(tResumeBaseInfo.getResumeId()));
                tResumeProjectService.saveBatch(tResumeProject);
            }
            if (!CollectionUtils.isEmpty(tResumeInterestJob)) {
                tResumeInterestJob.forEach(item -> item.setResumeId(tResumeBaseInfo.getResumeId()));
                tResumeInterestJobService.saveBatch(tResumeInterestJob);
            }
            log.info("保存简历成功");
        } else {
            // 修改简历信息
            // 查询当前用户的简历
            TResumeBaseInfo tResumeBaseInfoLatest = this.getOne(new LambdaQueryWrapper<TResumeBaseInfo>()
                    .eq(TResumeBaseInfo::getResumeId, saveResumeReq.getResumeId())
                    .eq(TResumeBaseInfo::getUserId, userId));
            if (null == tResumeBaseInfoLatest) {
                throw new BusinessException(10023, "当前用户没有该简历");
            }

            TResumeBaseInfo tResumeBaseInfo = saveResumeReq.getTResumeBaseInfo();
            List<TResumeWork> tResumeWork = saveResumeReq.getTResumeWork();
            List<TResumeEducation> tResumeEducation = saveResumeReq.getResumeEducation();
            List<TResumeProject> tResumeProject = saveResumeReq.getTResumeProject();
            List<TResumeInterestJob> tResumeInterestJob = saveResumeReq.getTResumeInterestJob();

            // 更新基本信息
            if (null != tResumeBaseInfo) {
                this.updateById(tResumeBaseInfo);
            }
            if (!CollectionUtils.isEmpty(tResumeWork)) {
                tResumeWorkService.remove(new LambdaQueryWrapper<TResumeWork>()
                        .eq(TResumeWork::getResumeId, tResumeBaseInfoLatest.getResumeId()));
                tResumeWorkService.saveBatch(tResumeWork);
            }
            if (!CollectionUtils.isEmpty(tResumeEducation)) {
                tResumeEducationService.remove(new LambdaQueryWrapper<TResumeEducation>()
                        .eq(TResumeEducation::getResumeId, tResumeBaseInfoLatest.getResumeId()));
                tResumeEducationService.saveBatch(tResumeEducation);
            }
            if (!CollectionUtils.isEmpty(tResumeProject)) {
                tResumeProjectService.remove(new LambdaQueryWrapper<TResumeProject>()
                        .eq(TResumeProject::getResumeId, tResumeBaseInfoLatest.getResumeId()));
                tResumeProjectService.saveBatch(tResumeProject);
            }
            if (!CollectionUtils.isEmpty(tResumeInterestJob)) {
                tResumeInterestJobService.remove(new LambdaQueryWrapper<TResumeInterestJob>()
                        .eq(TResumeInterestJob::getResumeId, tResumeBaseInfoLatest.getResumeId()));
                tResumeInterestJobService.saveBatch(tResumeInterestJob);
            }
            log.info("修改简历成功");
        }
    }
}
