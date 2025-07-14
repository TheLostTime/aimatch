package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.example.entity.ResponseResult;
import com.example.req.HrActivateReq;
import com.example.req.HrJoinCompanyReq;
import com.example.req.SavePositionReq;
import com.example.resp.HrInfoResp;
import com.example.resp.PositionDetailResp;
import com.example.service.DeepSeekService;
import com.example.service.TCompanyService;
import com.example.service.THrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.Map;

@Api(value = "HR", tags = {"HR相关"})
@RestController
@RequestMapping("/v1/hr")
@Slf4j
public class HRController {

    private static final Logger logger = LoggerFactory.getLogger(HRController.class);

    @Autowired
    private TCompanyService companyService;

    @Autowired
    private THrService thrService;


    @Autowired
    private DeepSeekService deepSeekService;


    @ApiOperation(value = "hr个人信息+加入公司", notes = "", httpMethod = "POST")
    @SaCheckLogin
    @PostMapping("/company/join")
    public ResponseResult<?> join(@RequestBody HrJoinCompanyReq hrJoinCompany) {
        companyService.joinCompany(hrJoinCompany);
        return ResponseResult.success("成功加入公司");
    }

    @ApiOperation(value = "个人实名认证", notes = "", httpMethod = "GET")
    @SaCheckLogin
    @GetMapping("/realName/auth/apply")
    public ResponseResult<?> realName() {
        thrService.realName();
        return ResponseResult.success();
    }

    @ApiOperation(value = "查询HR信息和状态", notes = "", httpMethod = "GET")
    @SaCheckLogin
    @GetMapping("/hr/status")
    public ResponseResult<HrInfoResp> getHrStatus() {
        HrInfoResp hrInfo = companyService.getHrStatus();
        return ResponseResult.success(hrInfo);
    }



    @SaCheckLogin
    @PostMapping("/enterprise/certification/apply")
    @ApiOperation(value = "申请企业认证", notes = "", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "file", name = "enterpriseLicenseFile", value = "", required = false),
            @ApiImplicitParam(paramType = "form", dataType = "file", name = "incumbencyCertificateFile", value = "", required = false)
    })
    public ResponseResult<?> applyCertification(@RequestPart @RequestParam("enterpriseLicenseFile") MultipartFile enterpriseLicenseFile,
                                                @RequestPart @RequestParam("incumbencyCertificateFile") MultipartFile incumbencyCertificateFile) {
        companyService.applyCertification(enterpriseLicenseFile, incumbencyCertificateFile);
        return ResponseResult.success();
    }


    @ApiOperation(value = "岗位保存草稿", notes = "", httpMethod = "POST")
    @SaCheckLogin
    @PostMapping("/position/save")
    public ResponseResult<?> savePosition(@RequestBody SavePositionReq savePositionReq) {
        try {
            companyService.savePosition(savePositionReq);
        } catch (Exception e) {
            return ResponseResult.error(e.getMessage());
        }
        return ResponseResult.success(true);
    }


    @ApiOperation(value = "发布岗位", notes = "", httpMethod = "POST")
    @SaCheckLogin
    @PostMapping("/position/publish")
    public ResponseResult<?> publishPosition(@RequestParam("positionId") String positionId) {
        try {
            companyService.publishPosition(positionId);
        } catch (Exception e) {
            return ResponseResult.error(e.getMessage());
        }
        return ResponseResult.success(true);
    }

    @ApiOperation(value = "查询岗位详情", notes = "", httpMethod = "POST")
    @SaCheckLogin
    @PostMapping("/position/detail")
    public ResponseResult<PositionDetailResp> getPositionDetail(@RequestParam("positionId") String positionId) {
        PositionDetailResp positionDetailResp = companyService.getPositionDetail(positionId);
        return ResponseResult.success(positionDetailResp);
    }


    @ApiOperation(value = "下线岗位", notes = "", httpMethod = "POST")
    @SaCheckLogin
    @PostMapping("/position/offline")
    public ResponseResult<?> offlinePosition(@RequestParam("positionId") String positionId) {
        try {
            companyService.offlinePosition(positionId);
        } catch (Exception e) {
            return ResponseResult.error(e.getMessage());
        }
        return ResponseResult.success(true);
    }


    @ApiOperation(value = "HR开通vip", notes = "", httpMethod = "POST")
    @SaCheckLogin
    @PostMapping("/vip/activate")
    public ResponseResult<?> activateVip(@RequestBody HrActivateReq hrActivateReq) {
        try {
            companyService.activateVip(hrActivateReq);
        } catch (Exception e) {
            return ResponseResult.error(e.getMessage());
        }
        return ResponseResult.success(true);
    }

    @ApiOperation(value = "升级vip", notes = "", httpMethod = "POST")
    @SaCheckLogin
    @PostMapping("/vip/upgrade")
    public ResponseResult<?> upgradeVip(@RequestBody HrActivateReq hrActivateReq) {
        try {
            companyService.upgradeVip(hrActivateReq);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error(e.getMessage());
        }
        return ResponseResult.success(true);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "Map<String, Object>", name = "request", value = "{\"content\":\"\"}", required = true)
    })
    @ApiOperation(value = "HR发起大模型对话（生成岗位描述）", notes = "", httpMethod = "POST")
    @PostMapping(value = "/chat/completions", produces = "text/event-stream;charset=UTF-8")
    public Flux<String> chat(@RequestBody Map<String, Object> request) {
        String content = (String) request.get("content");
        return deepSeekService.chatFlux(content);
    }





}