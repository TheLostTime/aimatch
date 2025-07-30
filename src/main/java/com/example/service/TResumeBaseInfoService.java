package com.example.service;

import com.example.entity.TEmployeeResumeFile;
import com.example.entity.TResumeBaseInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.req.GetJobListReq;
import com.example.req.SaveResumeReq;
import com.example.resp.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface TResumeBaseInfoService extends IService<TResumeBaseInfo>{

    void saveResume(SaveResumeReq saveResumeReq);

    EmployeeStatusResp getEmployeeStatus();

    void uploadResumeFile(MultipartFile resumeFile,String FileName);

    void deleteResumeFile(String fileId);

    List<TEmployeeResumeFile> getResumeFile(String resumeId);

    void activateVip(String vipType, String spec);

    String imNewMessage(String positionId, String resumeId);

    void createMessage(String positionId, String resumeId, String message, String messageType);

    ResumeDetailResp getResumeDetail(String resumeId);

    ResumeDetailHrResp getResumeDetailHr(String resumeId,String positionId);

    void hrSaveResume(String resumeId,String positionId);

    void downloadResumeFile(String fileId, HttpServletResponse response);

    void hrCancelResume(String resumeId, String positionId);
}
