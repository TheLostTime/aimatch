package com.example.controller;

import com.example.entity.ResponseResult;
import com.example.req.GetJobListReq;
import com.example.resp.GetJobListResp;
import com.example.service.TCompanyService;
import com.example.service.TResumeBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "访客", tags = {"访客"})
@RestController
@RequestMapping("/guest")
@Slf4j
public class GuestController {

    @Autowired
    private TResumeBaseInfoService tResumeBaseInfoService;

    @Autowired
    private TCompanyService tCompanyService;

    @GetMapping("/job/list")
    @ApiOperation(value = "查看职位列表", notes = "", httpMethod = "GET")
    public ResponseResult<List<GetJobListResp>> getJobList(@ModelAttribute GetJobListReq getJobListReq) {
        if (null == getJobListReq.getCurrentPage() && null == getJobListReq.getPageSize()) {
            getJobListReq.setCurrentPage(1);
            getJobListReq.setPageSize(9);
        }
        List<GetJobListResp> getJobListResp = tCompanyService.getJobList(getJobListReq);
        return ResponseResult.success(getJobListResp);
    }

}    