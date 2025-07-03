package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.example.entity.ResponseResult;
import com.example.entity.TResumeBaseInfo;
import com.example.req.SaveResumeReq;
import com.example.resp.EmployeeStatusResp;
import com.example.service.TEmployeeService;
import com.example.service.TPositionService;
import com.example.service.TResumeBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "求职者", tags = {"求职者"})
@RestController
@RequestMapping("/v1/employee")
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private TPositionService tPositionService;

    @Autowired
    private TResumeBaseInfoService tResumeBaseInfoService;

    @Autowired
    private TEmployeeService tEmployeeService;



    @ApiOperation(value = "查询求职者状态", notes = "", httpMethod = "GET")
    @SaCheckLogin
    @GetMapping("/status")
    public ResponseResult<EmployeeStatusResp> getEmployeeStatus() {
        EmployeeStatusResp resp = tResumeBaseInfoService.getEmployeeStatus();
        return ResponseResult.success(resp);
    }

    @ApiOperation(value = "创建/修改在线简历", notes = "", httpMethod = "POST")
    @SaCheckLogin
    @PostMapping("/resume/save")
    public ResponseResult<?> saveResume(@RequestBody SaveResumeReq saveResumeReq) {
        tResumeBaseInfoService.saveResume(saveResumeReq);
        return ResponseResult.success();
    }

}