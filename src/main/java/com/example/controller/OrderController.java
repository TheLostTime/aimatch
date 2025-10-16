package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.example.entity.ResponseResult;
import com.example.entity.TOrder;
import com.example.service.TOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "订单", tags = {"订单"})
@RestController
@RequestMapping("/v1/order")
@Slf4j
public class OrderController {

    @Autowired
    private TOrderService orderService;
    @ApiOperation(value = "创建订单", notes = "", httpMethod = "POST")
    @SaCheckLogin
    @PostMapping("/createOrder")
    public ResponseResult<TOrder> createOrder(@RequestParam String packageId) {
        TOrder tOrder = orderService.createOrder(packageId);
        return ResponseResult.success(tOrder);
    }

    @ApiOperation(value = "获取订单列表", notes = "", httpMethod = "GET")
    @SaCheckLogin
    @GetMapping("/getOrder")
    public ResponseResult<List<TOrder>> getOrder(@RequestParam(value = "status",required = false) Integer status) {
        List<TOrder> orderList = orderService.getCommonOrder(status);
        return ResponseResult.success(orderList);
    }


}