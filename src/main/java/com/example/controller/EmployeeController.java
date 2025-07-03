package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.example.entity.ResponseResult;
import com.example.entity.TEmployeeResumeFile;
import com.example.entity.TEmployeeSaveJob;
import com.example.entity.TResumeBaseInfo;
import com.example.req.SaveJobReq;
import com.example.req.SaveResumeReq;
import com.example.resp.ChatSessionResp;
import com.example.resp.EmployeeStatusResp;
import com.example.resp.GetSaveJobListResp;
import com.example.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @Autowired
    private TEmployeeSaveJobService tEmployeeSaveJobService;

    @Autowired
    private THrMarkResumeService tHrMarkResumeService;



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

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "file", name = "resumeFile", value = "", required = true),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "fileName", value = "", required = true)
    })
    @ApiOperation(value = "上传附件简历", notes = "", httpMethod = "POST")
    @SaCheckLogin
    @PostMapping("/resume/file/upload")
    public ResponseResult<Boolean> uploadResumeFile(
            @RequestPart @RequestParam("resumeFile") MultipartFile resumeFile,
            @RequestParam("fileName") String fileName) {
        tResumeBaseInfoService.uploadResumeFile(resumeFile,fileName);
        return ResponseResult.success(true);
    }

    @ApiOperation(value = "删除附件简历", notes = "", httpMethod = "POST")
    @SaCheckLogin
    @PostMapping("/resume/file/delete")
    public ResponseResult<Boolean> deleteResumeFile(
            @RequestParam("fileId") String fileId) {
        tResumeBaseInfoService.deleteResumeFile(fileId);
        return ResponseResult.success(true);
    }

    @ApiOperation(value = "查询个人所有附件简历", notes = "", httpMethod = "GET")
    @SaCheckLogin
    @GetMapping("/resume/file")
    public ResponseResult<List<TEmployeeResumeFile>> getResumeFile() {
        List<TEmployeeResumeFile> resumeFiles = tResumeBaseInfoService.getResumeFile();
        return ResponseResult.success(resumeFiles);
    }


    @ApiOperation(value = "收藏/取消收藏职位", notes = "", httpMethod = "POST")
    @SaCheckLogin
    @PostMapping("/job/save")
    public ResponseResult<?> saveJob(@RequestBody SaveJobReq saveJobReq) {
        tEmployeeSaveJobService.saveJob(saveJobReq);
        return ResponseResult.success();
    }

    @ApiOperation(value = "查询收藏状态", notes = "", httpMethod = "GET")
    @SaCheckLogin
    @GetMapping("/job/save/status")
    public ResponseResult<?> getSaveJobStatus(@RequestParam("positionId") String positionId) {
        TEmployeeSaveJob tEmployeeSaveJob = tEmployeeSaveJobService.getSaveJobStatus(positionId);
        return ResponseResult.success(tEmployeeSaveJob);
    }

    @ApiOperation(value = "查询收藏的职位列表", notes = "", httpMethod = "GET")
    @SaCheckLogin
    @GetMapping("/job/save/list")
    public ResponseResult<List<GetSaveJobListResp>> getSaveJobList() {
        List<GetSaveJobListResp> tEmployeeSaveJobs = tEmployeeSaveJobService.getSaveJobList();
        return ResponseResult.success(tEmployeeSaveJobs);
    }

    @ApiOperation(value = "求职者开通VIP", notes = "", httpMethod = "POST")
    @SaCheckLogin
    @PostMapping("/vip/activate")
    public ResponseResult<Boolean> activateVip(@RequestParam("vipType") String vipType,
                                               @RequestParam("spec") String spec) {
        tResumeBaseInfoService.activateVip(vipType,spec);
        return ResponseResult.success(true);
    }

    @ApiOperation(value = "求职者发起打招呼", notes = "", httpMethod = "POST")
    @SaCheckLogin
    @PostMapping("/im/new-message")
    public ResponseResult<String> imNewMessage(@RequestParam("positionId") String positionId,
                                               @RequestParam("resumeId") String resumeId) {
        String resumeStatus = tResumeBaseInfoService.imNewMessage(positionId,resumeId);
        return ResponseResult.success(resumeStatus);
    }

    @ApiOperation(value = "发送聊天消息", notes = "", httpMethod = "POST")
    @SaCheckLogin
    @PostMapping("/im/message/create")
    public ResponseResult<Boolean> createMessage(@RequestParam("positionId") String positionId,
                                                @RequestParam("resumeId") String resumeId,
                                                @RequestParam("message") String message,
                                                @RequestParam("messageType") String messageType) {
        tResumeBaseInfoService.createMessage(positionId,resumeId,message,messageType);
        return ResponseResult.success(true);
    }

    @ApiOperation(value = "查询聊天会话列表", notes = "", httpMethod = "GET")
    @SaCheckLogin
    @GetMapping("/im/chat-session/list")
    public ResponseResult<List<ChatSessionResp>> chatSessionList(
            @RequestParam(value = "positionId", required = false) String positionId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "resumeStatus", required = false) String resumeStatus) {
        List<ChatSessionResp> chatSessionResp = tHrMarkResumeService.chatSessionList(positionId,name,resumeStatus);
        return ResponseResult.success(chatSessionResp);
    }











}