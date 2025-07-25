package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.example.entity.ResponseResult;
import com.example.entity.TCompany;
import com.example.entity.TPosition;
import com.example.req.AuditCompanyReq;
import com.example.req.AuditPositionReq;
import com.example.resp.QueryPositionManageResp;
import com.example.service.TCompanyService;
import com.example.service.TPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.constant.Constants.USER_TYPE_OPERATION;

@Api(value = "管理员后台", tags = {"管理员后台"})
@RestController
@RequestMapping("/manage")
public class ManagerController {

    private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

    @Autowired
    private TCompanyService companyService;

    @Autowired
    private TPositionService positionService;


    @ApiOperation(value = "查询所有企业列表", notes = "", httpMethod = "GET")
//    @SaCheckLogin
    @GetMapping("/company/list")
    public ResponseResult<List<TCompany>> queryCompanyList() {
        List<TCompany> companyList = companyService.queryCompanyList();
        return ResponseResult.success(companyList);
    }

    @ApiOperation(value = "查询企业详情", notes = "", httpMethod = "GET")
    @SaCheckLogin
    @GetMapping("/company/detail")
    public ResponseResult<TCompany> queryCompanyDetail(@RequestParam("companyId") String companyId) {
        TCompany company = companyService.getById(companyId);
        return ResponseResult.success(company);
    }

    @ApiOperation(value = "企业审核", notes = "", httpMethod = "POST")
    @SaCheckRole(value = {USER_TYPE_OPERATION})
    @PostMapping("/company/audit")
    public ResponseResult<?> auditCompany(@RequestBody AuditCompanyReq auditCompanyReq) {
        companyService.auditCompany(auditCompanyReq);
        return ResponseResult.success();
    }

    @ApiOperation(value = "查询岗位列表", notes = "", httpMethod = "GET")
    @SaCheckLogin
    @GetMapping("/position/list")
    public ResponseResult<List<QueryPositionManageResp>> queryPositionManageList(
            @RequestParam(value = "positionStatus",required = false) String positionStatus) {
        List<QueryPositionManageResp> tPositionList = positionService.queryPositionManageList(positionStatus);
        return ResponseResult.success(tPositionList);
    }

    @ApiOperation(value = "查询岗位详情", notes = "", httpMethod = "GET")
    @SaCheckLogin
    @GetMapping("/position/detail")
    public ResponseResult<TPosition> queryPositionDetail(@RequestParam("positionId") String positionId) {
        TPosition position = positionService.getById(positionId);
        return ResponseResult.success(position);
    }

    @ApiOperation(value = "岗位审核", notes = "", httpMethod = "POST")
    @SaCheckRole(value = {USER_TYPE_OPERATION})
    @PostMapping("/position/audit")
    public ResponseResult<?> auditPosition(@RequestBody AuditPositionReq auditPosition) {
        positionService.auditPosition(auditPosition);
        return ResponseResult.success();
    }
}