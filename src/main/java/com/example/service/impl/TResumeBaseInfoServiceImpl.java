package com.example.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.*;
import com.example.exception.BusinessException;
import com.example.mapper.TResumeBaseInfoMapper;
import com.example.req.SaveResumeReq;
import com.example.resp.ChatSessionResp;
import com.example.resp.EmployeeStatusResp;
import com.example.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.shaded.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.constant.Constants.*;

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

    @Autowired
    private TEmployeeVipService tEmployeeVipService;

    @Autowired
    private TEmployeeResumeFileService tEmployeeResumeFileService;

    @Autowired
    private THrMarkResumeService tHrMarkResumeService;

    @Autowired
    private TPositionService tPositionService;

    @Autowired
    private TImMessageService tImMessageService;

    @Value("${upload.resumeFilePath}")
    private String resumeFilePath;


    @Override
    @Transactional
    public void saveResume(SaveResumeReq saveResumeReq) {
        // 获取用户ID
        String userId = StpUtil.getLoginId().toString();
        if (StringUtils.isEmpty(saveResumeReq.getResumeId())) {
            // 防止重复提交
            TEmployee tEmployeeLast = tEmployeeService.getOne(new LambdaQueryWrapper<TEmployee>()
                    .eq(TEmployee::getUserId, userId));
            if (null != tEmployeeLast) {
                throw new BusinessException(10031, "您已提交过简历");
            }
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
                tEmployee.setAge(DateUtil.ageOfNow(tEmployee.getBirthDate()+"-01"));
            }
            tEmployee.setUserId(userId);
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
            // 当前resumeID只是一个标记，获取数据库真实的resumeID
            TResumeBaseInfo tResumeBaseInfoLast = this.getOne(new LambdaQueryWrapper<TResumeBaseInfo>()
                    .eq(TResumeBaseInfo::getUserId, StpUtil.getLoginId().toString()));
            if (null == tResumeBaseInfoLast || !tResumeBaseInfoLast.getResumeId().equals(saveResumeReq.getResumeId())) {
                throw new BusinessException(10023, "你的简历ID已失效");
            }
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
                tResumeBaseInfo.setResumeId(saveResumeReq.getResumeId());
                tResumeBaseInfo.setUserId(userId);
                this.updateById(tResumeBaseInfo);
            }
            if (!CollectionUtils.isEmpty(tResumeWork)) {
                tResumeWorkService.remove(new LambdaQueryWrapper<TResumeWork>()
                        .eq(TResumeWork::getResumeId, saveResumeReq.getResumeId()));
                tResumeWork.forEach(item -> item.setResumeId(saveResumeReq.getResumeId()));
                tResumeWorkService.saveBatch(tResumeWork);
            }
            if (!CollectionUtils.isEmpty(tResumeEducation)) {
                tResumeEducationService.remove(new LambdaQueryWrapper<TResumeEducation>()
                        .eq(TResumeEducation::getResumeId, saveResumeReq.getResumeId()));
                tResumeEducation.forEach(item -> item.setResumeId(saveResumeReq.getResumeId()));
                tResumeEducationService.saveBatch(tResumeEducation);
            }
            if (!CollectionUtils.isEmpty(tResumeProject)) {
                tResumeProjectService.remove(new LambdaQueryWrapper<TResumeProject>()
                        .eq(TResumeProject::getResumeId, saveResumeReq.getResumeId()));
                tResumeProject.forEach(item -> item.setResumeId(saveResumeReq.getResumeId()));
                tResumeProjectService.saveBatch(tResumeProject);
            }
            if (!CollectionUtils.isEmpty(tResumeInterestJob)) {
                tResumeInterestJobService.remove(new LambdaQueryWrapper<TResumeInterestJob>()
                        .eq(TResumeInterestJob::getResumeId, saveResumeReq.getResumeId()));
                tResumeInterestJob.forEach(item -> item.setResumeId(saveResumeReq.getResumeId()));
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
        tUser.setPassword(null);
        employeeStatusResp.setTUser(tUser);

        // 查询vip信息
        TEmployeeVip employeeVip = tEmployeeVipService.getById(userId);
        if (null != employeeVip && employeeVip.getVipType().equals(VIP_NORMAL)) {
            employeeStatusResp.setVipType(VIP_NORMAL);
        } else {
            employeeStatusResp.setVipType(VIP_NO);
        }
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

    @Override
    public void uploadResumeFile(MultipartFile resumeFile,String saveFileName) {
        if (resumeFile != null) {
            // 文件格式
            String fileNameExt =  FilenameUtils.getExtension(resumeFile.getOriginalFilename());
            // 创建目录
            File file1 = new File(resumeFilePath);
            if (!file1.exists()) {
                file1.mkdirs();
            }
            // 上传至指定目录
            String originalFilename = resumeFile.getOriginalFilename();
            String fileName = originalFilename.split("\\.")[0];
            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
            fileName = resumeFilePath + File.separator +fileName + "_" + sf.format(new Date()) + "." + fileNameExt;
            try {
                resumeFile.transferTo(new File(fileName));
            } catch (IOException e) {
                e.printStackTrace();
                throw new BusinessException(10023, "上传失败");
            }

            // 成功后更新表
            TEmployeeResumeFile tEmployeeResumeFile = TEmployeeResumeFile.builder()
                    .fileName(saveFileName)
                    .userId(StpUtil.getLoginId().toString())
                    .path(fileName)
                    .fileName(saveFileName)
                    .build();
            tEmployeeResumeFileService.save(tEmployeeResumeFile);
        }
    }

    @Override
    public void deleteResumeFile(String fileId) {
        // 删除简历
        TEmployeeResumeFile tEmployeeResumeFile = tEmployeeResumeFileService.getById(fileId);
        if (null == tEmployeeResumeFile) {
            throw new BusinessException(10023, "未找到该文件");
        }
        tEmployeeResumeFileService.removeById(fileId);
        File file = new File(tEmployeeResumeFile.getPath());
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    public List<TEmployeeResumeFile> getResumeFile() {
        return tEmployeeResumeFileService.list(new LambdaQueryWrapper<TEmployeeResumeFile>()
                .eq(TEmployeeResumeFile::getUserId, StpUtil.getLoginId().toString()));
    }

    @Override
    public void activateVip(String vipType, String spec) {
        // 查询是否已经激活过vip
        TEmployeeVip tEmployeeVipLast = tEmployeeVipService.getById(StpUtil.getLoginId().toString());
        if (null != tEmployeeVipLast) {
            throw new BusinessException(10007, "您已经激活过vip");
        }
        TEmployeeVip tEmployeeVip =  TEmployeeVip.builder()
                .vipType(vipType)
                .beginTime(DateUtil.date())
                .expireTime(DateUtil.offsetDay(DateUtil.date(), Integer.parseInt(spec)))
                .userId(StpUtil.getLoginId().toString())
                .build();
        tEmployeeVipService.save(tEmployeeVip);
    }

    @Override
    public String imNewMessage(String positionId, String resumeId) {
        //查询t_hr_mark_resume表，看是否有记录
        THrMarkResume tHrMarkResumeLast = tHrMarkResumeService.getOne(new LambdaQueryWrapper<THrMarkResume>()
                .eq(THrMarkResume::getPositionId, positionId)
                .eq(THrMarkResume::getResumeId, resumeId));
        if (null == tHrMarkResumeLast) {
            // 查询岗位获取userId
            TPosition tPosition = tPositionService.getById(positionId);
            if (null == tPosition) {
                throw new BusinessException(10005, "岗位不存在");
            }
            // 还没有沟通过
            THrMarkResume tHrMarkResume = THrMarkResume.builder()
                    .resumeId(resumeId)
                    .resumeStatus(RESUME_STATUS_NEW_MESSAGE)
                    .hrUserId(tPosition.getUserId())
                    .createTime(DateUtil.date())
                    .employeeUserId(StpUtil.getLoginId().toString())
                    .positionId(positionId)
                    .saveFlag(false)
                    .updateTime(DateUtil.date())
                    .build();
            tHrMarkResumeService.save(tHrMarkResume);
            return RESUME_STATUS_NEW_MESSAGE;
        } else {
            // 啥也不做
            return null;
        }
    }

    @Override
    @Transactional
    public void createMessage(String positionId, String resumeId, String message, String messageType) {
        // 查询当前表状态
        THrMarkResume tHrMarkResume = tHrMarkResumeService.getOne(new LambdaQueryWrapper<THrMarkResume>()
                .eq(THrMarkResume::getPositionId, positionId)
                .eq(THrMarkResume::getResumeId, resumeId));
        // 查询用户
        TUser tUser = tUserService.getById(StpUtil.getLoginId().toString());
        if (messageType.equals(MESSAGE_TYPE_HR_SAY_HELLO)) {
            if (!tUser.getUserType().equals(USER_TYPE_HR) && !tUser.getUserType().equals(USER_TYPE_INTRODUCE)) {
                throw new BusinessException(10005, "非招聘者不能打招呼");
            }
            // HR发起打招呼
            if (null != tHrMarkResume) {
                throw new BusinessException(10005, "HR已经打过招呼");
            }
            // 查询简历获取userId
            TResumeBaseInfo tResumeBaseInfo = this.getById(resumeId);
            if (null == tResumeBaseInfo) {
                throw new BusinessException(10005, "简历不存在");
            }
            tHrMarkResume = THrMarkResume.builder()
                    .resumeId(resumeId)
                    .resumeStatus(RESUME_STATUS_SAY_HELLO)
                    .hrUserId(StpUtil.getLoginId().toString())
                    .createTime(DateUtil.date())
                    .employeeUserId(tResumeBaseInfo.getUserId())
                    .positionId(positionId)
                    .saveFlag(false)
                    .updateTime(DateUtil.date())
                    .build();
            tHrMarkResumeService.save(tHrMarkResume);

            // 保存消息
            TImMessage tMessage = TImMessage.builder()
                    .message(message)
                    .messageIndex(1)
                    .positionId(positionId)
                    .resumeId(resumeId)
                    .userId(StpUtil.getLoginId().toString())
                    .userType(tUser.getUserType())
                    .createTime(DateUtil.date())
                    .build();
            tImMessageService.save(tMessage);
            // TODO 记录累计打招呼次数

        }
        if (messageType.equals(MESSAGE_TYPE_NEW_MESSAGE)){
            if (!tUser.getUserType().equals(USER_TYPE_EMPLOYEE)) {
                throw new BusinessException(10005, "非求职者不能发送此类消息");
            }
            // 求职者发消息
            if (null == tHrMarkResume) {
                throw new BusinessException(10005, "求职者还没打过招呼");
            }

            // 求职者状态判断
            if (!RESUME_STATUS_NEW_MESSAGE.equals(tHrMarkResume.getResumeStatus())) {
                throw new BusinessException(10005, "打招呼状态不对");
            }

            // 更新状态为沟通
            tHrMarkResume.setResumeStatus(RESUME_STATUS_COMMUNICATING);
            tHrMarkResumeService.updateById(tHrMarkResume);

            // 插入消息
            TImMessage tMessage = TImMessage.builder()
                    .message(message)
                    .messageIndex(1)
                    .positionId(positionId)
                    .resumeId(resumeId)
                    .userId(StpUtil.getLoginId().toString())
                    .userType(tUser.getUserType())
                    .createTime(DateUtil.date())
                    .build();
            tImMessageService.save(tMessage);
        }

        if (messageType.equals(MESSAGE_TYPE_NORMAL)) {
            if (null == tHrMarkResume) {
                throw new BusinessException(10005, "当前消息发送者还没打过招呼");
            }
            // 有可能求职者，有可能是招聘者,所以当前状态只能是打招呼或者新消息
            if (RESUME_STATUS_NEW_MESSAGE.equals(tHrMarkResume.getResumeStatus()) ||
                    RESUME_STATUS_SAY_HELLO.equals(tHrMarkResume.getResumeStatus())) {
                // 更新表状态为沟通
                tHrMarkResume.setResumeStatus(RESUME_STATUS_COMMUNICATING);
                tHrMarkResumeService.updateById(tHrMarkResume);
            }

            // 获取最大messageIndex消息
            TImMessage tImMessage = tImMessageService.getOne(new LambdaQueryWrapper<TImMessage>()
                    .eq(TImMessage::getResumeId, resumeId)
                    .eq(TImMessage::getPositionId, positionId)
                    .orderByDesc(TImMessage::getMessageIndex)
                    .last("limit 1"));

            if (null == tImMessage) {
                throw new BusinessException(10006, "获取最大索引失败");
            }
            // 其他状态不变更,插入消息
            TImMessage tMessage = TImMessage.builder()
                    .message(message)
                    .messageIndex(tImMessage.getMessageIndex() + 1)
                    .positionId(positionId)
                    .resumeId(resumeId)
                    .userId(StpUtil.getLoginId().toString())
                    .userType(tUser.getUserType())
                    .createTime(DateUtil.date())
                    .build();
            tImMessageService.save(tMessage);
        }

    }


}
