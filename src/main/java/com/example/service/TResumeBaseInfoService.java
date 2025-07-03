package com.example.service;

import com.example.entity.TResumeBaseInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.req.GetJobListReq;
import com.example.req.SaveResumeReq;
import com.example.resp.EmployeeStatusResp;
import com.example.resp.GetJobListResp;

public interface TResumeBaseInfoService extends IService<TResumeBaseInfo>{

    void saveResume(SaveResumeReq saveResumeReq);

    EmployeeStatusResp getEmployeeStatus();

}
