package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import com.example.entity.ResponseResult;
import com.example.req.ResumeListReq;
import com.example.resp.ResumeDetailHrResp;
import com.example.resp.ResumeDetailResp;
import com.example.resp.ResumeListResp;
import com.example.resp.TalentListResp;
import com.example.service.TPositionService;
import com.example.service.TResumeBaseInfoService;
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

    @Autowired
    private TResumeBaseInfoService tResumeBaseInfoService;

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
    @ApiOperation(value = "取消简历标记为不合适或者约面试", notes = "将状态设置为communicating(取消约面或者取消不合适状态设置为沟通中)", httpMethod = "POST")
    @SaCheckRole(value = {"1", "2"}, mode = SaMode.OR)
    @PostMapping("/resume/cancelMark")
    public ResponseResult<Boolean> cancelMark(@RequestParam("resumeId") String resumeId,
                                              @RequestParam("positionId") String positionId) {
        tPositionService.cancelMark(resumeId,positionId);
        return ResponseResult.success(true);
    }


    @ApiOperation(value = "校验HR是否主动发起打招呼", notes = "", httpMethod = "GET")
    @SaCheckRole(value = {"1", "2"}, mode = SaMode.OR)
    @GetMapping("/resume/check-say-hello")
    public ResponseResult<Boolean> checkSayHello() {
        tPositionService.checkSayHello();
        return ResponseResult.success(true);
    }

    @ApiOperation(value = "校验是否能下载附件简历", notes = "", httpMethod = "GET")
    @SaCheckRole(value = {"1", "2"}, mode = SaMode.OR)
    @GetMapping("/resume/file/check-download")
    public ResponseResult<Boolean> checkDownload() {
        tPositionService.checkDownload();
        return ResponseResult.success(true);
    }

    @ApiOperation(value = "查询人才市场的简历详情 ", notes = "", httpMethod = "GET")
    @SaCheckRole(value = {"1", "2"}, mode = SaMode.OR)
    @GetMapping("/resume/detail")
    public ResponseResult<ResumeDetailHrResp> getResumeDetailHr(
            @RequestParam("resumeId") String resumeId,
            @RequestParam("positionId") String positionId) {
        ResumeDetailHrResp resumeDetailResp = tResumeBaseInfoService.getResumeDetailHr(resumeId,positionId);
        return ResponseResult.success(resumeDetailResp);
    }

    @ApiOperation(value = "HR收藏简历 ", notes = "", httpMethod = "POST")
    @SaCheckRole(value = {"1", "2"}, mode = SaMode.OR)
    @PostMapping("/resume/save")
    public ResponseResult<Boolean> hrSaveResume(@RequestParam("resumeId") String resumeId,
                                                @RequestParam("positionId") String positionId) {
        tResumeBaseInfoService.hrSaveResume(resumeId,positionId);
        return ResponseResult.success(true);
    }

    @ApiOperation(value = "HR取消收藏简历 ", notes = "", httpMethod = "POST")
    @SaCheckRole(value = {"1", "2"}, mode = SaMode.OR)
    @GetMapping("/resume/cancel")
    public ResponseResult<Boolean> hrCancelResume(@RequestParam("resumeId") String resumeId,
                                                @RequestParam("positionId") String positionId) {
        tResumeBaseInfoService.hrCancelResume(resumeId,positionId);
        return ResponseResult.success(true);
    }

}