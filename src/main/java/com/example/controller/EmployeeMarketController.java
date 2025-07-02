package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.example.entity.ResponseResult;
import com.example.req.ResumeListReq;
import com.example.resp.ResumeListResp;
import com.example.service.TPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "人才市场", tags = {"人才市场"})
@RestController
@RequestMapping("/v1/employee-market")
public class EmployeeMarketController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeMarketController.class);

    @Autowired
    private TPositionService tPositionService;

    @ApiOperation(value = "查询人才市场的推荐人才列表", notes = "", httpMethod = "GET")
    @SaCheckLogin
    @GetMapping("/resume/list")
    public ResponseResult<ResumeListResp> getResumeList(ResumeListReq resumeListReq) {
        ResumeListResp resumeListResp = tPositionService.getResumeList(resumeListReq);
        return ResponseResult.success(resumeListResp);
    }


}