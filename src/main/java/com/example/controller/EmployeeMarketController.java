package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import com.example.entity.ResponseResult;
import com.example.req.ResumeListReq;
import com.example.resp.ResumeListResp;
import com.example.resp.TalentListResp;
import com.example.service.TPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "人才市场", tags = {"人才市场"})
@RestController
@RequestMapping("/v1/employee-market")
@Slf4j
public class EmployeeMarketController {

    @Autowired
    private TPositionService tPositionService;

    @ApiOperation(value = "查询人才市场的推荐人才列表", notes = "", httpMethod = "GET")
    @SaCheckRole(value = {"1", "2"}, mode = SaMode.OR)
    @GetMapping("/resume/list")
    public ResponseResult<List<ResumeListResp>> getResumeList(@Valid ResumeListReq resumeListReq) {
        List<ResumeListResp> resumeListResp = tPositionService.getResumeList(resumeListReq);
        return ResponseResult.success(resumeListResp);
    }

    @ApiOperation(value = "查询人才管理列表", notes = "", httpMethod = "GET")
    @SaCheckRole(value = {"1", "2"}, mode = SaMode.OR)
    @GetMapping("/talent/list")
    public ResponseResult<List<TalentListResp>> getTalentList(
            @RequestParam(value = "positionId",required = false) String positionId,
            @RequestParam(value = "resumeStatus",required = false) String resumeStatus) {
        List<TalentListResp> talentList = tPositionService.getTalentList(positionId,resumeStatus);
        return ResponseResult.success(talentList);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "resumeId", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "positionId", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "status", value = "interview|not_suit", required = true)
    })
    @ApiOperation(value = "简历标记为不合适或者约面试", notes = "", httpMethod = "POST")
    @SaCheckRole(value = {"1", "2"}, mode = SaMode.OR)
    @PostMapping("/resume/mark")
    public ResponseResult<Boolean> markResume(@RequestParam("resumeId") String resumeId,
                                              @RequestParam("positionId") String positionId,
                                              @RequestParam("status") String status) {
        tPositionService.markResume(resumeId,positionId,status);
        return ResponseResult.success(true);
    }

}