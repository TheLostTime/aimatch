package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.example.entity.ResponseResult;
import com.example.entity.TPosition;
import com.example.entity.TVipPackage;
import com.example.req.*;
import com.example.resp.HrInfoResp;
import com.example.resp.PositionDetailResp;
import com.example.service.*;
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

import javax.validation.Valid;
import java.util.List;

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
    private TPositionService tPositionService;

    @Autowired
    private DeepSeekService deepSeekService;


    @ApiOperation(value = "hr个人信息+加入公司", notes = "", httpMethod = "POST")
    @SaCheckLogin
    @PostMapping("/company/join")
    public ResponseResult<String> join(@ModelAttribute HrJoinCompanyReq hrJoinCompany) {
        String companyId = companyService.joinCompany(hrJoinCompany);
        return ResponseResult.success(companyId);
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

    @ApiOperation(value = "查询vip套餐", notes = "", httpMethod = "GET")
    @SaCheckLogin
    @GetMapping("/vip/package")
    public ResponseResult<List<TVipPackage>> getVipPackage() {
        List<TVipPackage> vipPackages = companyService.getVipPackage();
        return ResponseResult.success(vipPackages);
    }

    @ApiOperation(value = "HR开通vip", notes = "", httpMethod = "POST")
    @SaCheckLogin
    @PostMapping("/vip/activate")
    public ResponseResult<?> activateVip(@RequestBody HrActivateReq hrActivateReq) {
        companyService.activateVip(hrActivateReq);
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

    @ApiOperation(value = "HR发起大模型对话", notes = "生成岗位描述|或者生成ai简历优势,当前对话是一次性，不保留上下文", httpMethod = "POST")
    @PostMapping(value = "/chat/completions", produces = "text/event-stream;charset=UTF-8")
    @SaCheckLogin
    public Flux<String> chatCompletions(@RequestBody @Valid ChatReq chatReq) {
        return deepSeekService.chatFlux(chatReq.getContent(),false, null,StpUtil.getSession());
    }

    @ApiOperation(value = "笔试", notes = "第一次调用startExamFlag=true开启考试，后续startExamFlag=false", httpMethod = "POST")
    @PostMapping(value = "/chat/exam", produces = "text/event-stream;charset=UTF-8")
    @SaCheckLogin
    public Flux<String> chatExam(@RequestBody @Valid ChatExamReq chatExamReq) {
        TPosition tPosition = tPositionService.getById(chatExamReq.getPositionId());
        if (chatExamReq.getStartExamFlag()) {
            StringBuilder sb = new StringBuilder();
            sb.append("现在假设你是个面试官，我是面试者，你要面试一个岗位，强制要求每轮问一个，不要一次性出完所有题目，等我回答完成后问下一个，对我的回答不用做出评价，等所有问题问完后，生成最终得分，你要向我提" + chatExamReq.getExamNum() +"个的面试问题，满分100分。笔试要求如下：");
            sb.append(tPosition.getPositionDescription());
            chatExamReq.setContent(sb.toString());
        } else {
            // 将回答内容进行改造
            chatExamReq.setContent(chatExamReq.getContent() + "我的答案是：" + chatExamReq.getContent() + "。继续下一题");
        }
        return deepSeekService.chatFluxExam(true, StpUtil.getSession(),chatExamReq,StpUtil.getLoginId().toString());
    }

}