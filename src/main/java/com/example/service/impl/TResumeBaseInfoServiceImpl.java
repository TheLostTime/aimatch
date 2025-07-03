package com.example.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.*;
import com.example.exception.BusinessException;
import com.example.mapper.TResumeBaseInfoMapper;
import com.example.req.GetJobListReq;
import com.example.req.SaveResumeReq;
import com.example.resp.EmployeeStatusResp;
import com.example.resp.GetJobListResp;
import com.example.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class TResumeBaseInfoServiceImpl extends ServiceImpl<TResumeBaseInfoMapper, TResumeBaseInfo> implements TResumeBaseInfoService{

    @Autowired
    private TEmployeeService tEmployeeService;

    @Autowired
    private TResumeWorkService tResumeWorkService;

    @Autowired
    private TResumeEducationService tResumeEducationService;

    @Autowired
    private TResumeProjectService tResumeProjectService;

    @Autowired
    private TResumeInterestJobService tResumeInterestJobService;

    @Autowired
    private TUserService tUserService;

    @Override
    @Transactional
    public void saveResume(SaveResumeReq saveResumeReq) {
        // 获取用户ID
        String userId = StpUtil.getLoginId().toString();
        if (StringUtils.isEmpty(saveResumeReq.getResumeId())) {
            TEmployee tEmployee = saveResumeReq.getTEmployee();
            TResumeBaseInfo tResumeBaseInfo = saveResumeReq.getTResumeBaseInfo();
            List<TResumeWork> tResumeWork = saveResumeReq.getTResumeWork();
            List<TResumeEducation> tResumeEducation = saveResumeReq.getResumeEducation();
            List<TResumeProject> tResumeProject = saveResumeReq.getTResumeProject();
            List<TResumeInterestJob> tResumeInterestJob = saveResumeReq.getTResumeInterestJob();

            // 先保存求职者身份信息
            if (null == tEmployee) {
                throw new BusinessException(10021, "请填写求职者个人信息");
            }
            if (null != tEmployee.getBirthDate()) {
                tEmployee.setAge(DateUtil.ageOfNow(tEmployee.getBirthDate()));
            }
            tEmployeeService.save(tEmployee);

            if (null == tResumeBaseInfo){
                throw new BusinessException(10022, "请填写简历基础信息");
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
            log.info("保存成功");
        } else {
            // 修改求职者基本信息
            TEmployee tEmployee = saveResumeReq.getTEmployee();
            if (null != tEmployee) {
                tEmployee.setUserId(userId);
                tEmployeeService.updateById(tEmployee);
            }
            // 修改简历信息
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
                        .eq(TResumeWork::getResumeId, saveResumeReq.getResumeId()));
                tResumeWorkService.saveBatch(tResumeWork);
            }
            if (!CollectionUtils.isEmpty(tResumeEducation)) {
                tResumeEducationService.remove(new LambdaQueryWrapper<TResumeEducation>()
                        .eq(TResumeEducation::getResumeId, saveResumeReq.getResumeId()));
                tResumeEducationService.saveBatch(tResumeEducation);
            }
            if (!CollectionUtils.isEmpty(tResumeProject)) {
                tResumeProjectService.remove(new LambdaQueryWrapper<TResumeProject>()
                        .eq(TResumeProject::getResumeId, saveResumeReq.getResumeId()));
                tResumeProjectService.saveBatch(tResumeProject);
            }
            if (!CollectionUtils.isEmpty(tResumeInterestJob)) {
                tResumeInterestJobService.remove(new LambdaQueryWrapper<TResumeInterestJob>()
                        .eq(TResumeInterestJob::getResumeId, saveResumeReq.getResumeId()));
                tResumeInterestJobService.saveBatch(tResumeInterestJob);
            }
            log.info("修改成功");
        }
    }

    @Override
    public EmployeeStatusResp getEmployeeStatus() {
        EmployeeStatusResp employeeStatusResp = new EmployeeStatusResp();
        // 获取用户信息
        String userId = StpUtil.getLoginId().toString();
        TUser tUser = tUserService.getById(userId);
        employeeStatusResp.setTUser(tUser);

        // 获取当前用户简历
        TResumeBaseInfo tResumeBaseInfo = this.getOne(
                new LambdaQueryWrapper<TResumeBaseInfo>().eq(TResumeBaseInfo::getUserId, userId));

        if (null == tResumeBaseInfo) {
            log.warn("未找到简历信息...");
        } else {
            // 获取用户求职期望
            List<TResumeInterestJob> resumeInterestJobs = tResumeInterestJobService.list(new LambdaQueryWrapper<TResumeInterestJob>()
                    .eq(TResumeInterestJob::getResumeId, tResumeBaseInfo.getResumeId()));
            employeeStatusResp.setTResumeInterestJob(resumeInterestJobs);
        }
        return employeeStatusResp;
    }

}
