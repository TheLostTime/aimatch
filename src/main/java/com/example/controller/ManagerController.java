package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.*;
import com.example.req.AuditCompanyReq;
import com.example.req.AuditPositionReq;
import com.example.resp.QueryCompanyResp;
import com.example.resp.QueryPositionManageByPageResp;
import com.example.resp.QueryPositionManageResp;
import com.example.service.TCompanyService;
import com.example.service.TOrderService;
import com.example.service.TPositionService;
import com.example.service.TVipPackageService;
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

    @Autowired
    private TVipPackageService tVipPackageService;

    @Autowired
    private TOrderService orderService;


    @ApiOperation(value = "查询所有企业列表", notes = "", httpMethod = "GET")
//    @SaCheckLogin
    @GetMapping("/company/list")
    public ResponseResult<QueryCompanyResp> queryCompanyList(@RequestParam(value = "currentPage",required = false) Integer currentPage,
                                                           @RequestParam(value = "pageSize",required = false) Integer pageSize) {
        if (currentPage == null && pageSize == null) {
            currentPage = 1;
            pageSize = 10;
        }
        QueryCompanyResp queryCompanyResp = companyService.queryCompanyList(currentPage, pageSize);
        return ResponseResult.success(queryCompanyResp);
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
    public ResponseResult<QueryPositionManageByPageResp> queryPositionManageList(
            @RequestParam(value = "positionStatus",required = false) String positionStatus,
            @RequestParam(value = "currentPage",required = false) Integer currentPage,
            @RequestParam(value = "pageSize",required = false) Integer pageSize) {
        if (currentPage == null && pageSize == null) {
            currentPage = 1;
            pageSize = 10;
        }
        QueryPositionManageByPageResp tPositionList = positionService.queryPositionManageList(positionStatus,currentPage,pageSize);
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

    @ApiOperation(value = "管理员获取订单列表", notes = "", httpMethod = "GET")
    @SaCheckRole(value = {USER_TYPE_OPERATION})
    @GetMapping("/getOrder")
    public ResponseResult<IPage<TOrder>> getOrder(@RequestParam(value = "pageNum",required = false) Integer pageNum,
                                                  @RequestParam(value = "pageSize",required = false) Integer pageSize,
                                                  @RequestParam(value = "status",required = false) Integer status) {
        if (null == pageNum && null == pageSize) {
            pageNum = 1;
            pageSize = 10000;
        }
        IPage<TOrder> orderList = orderService.getOrder(pageNum, pageSize,status);
        return ResponseResult.success(orderList);
    }

    @ApiOperation(value = "查询套餐包", notes = "", httpMethod = "GET")
    @SaCheckRole(value = {USER_TYPE_OPERATION})
    @GetMapping("/package/query")
    public ResponseResult<List<TVipPackage>> queryPackage() {
        List<TVipPackage> vipPackages = tVipPackageService.list();
        return ResponseResult.success(vipPackages);
    }

    @ApiOperation(value = "修改套餐包（包括上架下架）", notes = "", httpMethod = "POST")
    @SaCheckRole(value = {USER_TYPE_OPERATION})
    @PostMapping("/package/update")
    public ResponseResult<?> updatePackage(@RequestBody TVipPackage tVipPackage) {
        tVipPackageService.updateById(tVipPackage);
        return ResponseResult.success();
    }

    @ApiOperation(value = "新增套餐包", notes = "", httpMethod = "POST")
    @SaCheckRole(value = {USER_TYPE_OPERATION})
    @PostMapping("/package/save")
    public ResponseResult<?> savePackage(@RequestBody TVipPackage tVipPackage) {
        tVipPackageService.save(tVipPackage);
        return ResponseResult.success();
    }
}