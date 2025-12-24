package com.example.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.example.entity.ResponseResult;
import com.example.service.TPaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(value = "支付", tags = {"支付"})
@RestController
@RequestMapping("/v1/payment")
@Slf4j
public class PaymentController {

    @Autowired
    private TPaymentService paymentService;

    @PostMapping("/qrcode")
//    @SaCheckLogin
    @ApiOperation(value = "创建支付二维码", notes = "", httpMethod = "POST")
    public ResponseResult<String> createPayQrcode(@RequestParam String orderId, @RequestParam Integer channel,
                                                  HttpServletRequest request) {
        log.info("创建支付二维码....");
        String remoteAddress = request.getRemoteAddr();
        String qrcode = paymentService.createPayQrcode(orderId, channel,remoteAddress);
        return ResponseResult.success(qrcode);
    }

    @PostMapping("/wx/notify")
    @ApiOperation(value = "微信支付回调", notes = "", httpMethod = "POST")
    public String wxPayNotify(HttpServletRequest request) {
        log.info("微信支付回调....");
        return paymentService.handleCallback(1, request);
    }

    @PostMapping("/alipay/notify")
    @ApiOperation(value = "支付宝支付回调", notes = "", httpMethod = "POST")
    public String aliPayNotify(HttpServletRequest request) {
        log.info("支付宝支付回调....");
        return paymentService.handleCallback(2, request);
    }

    @GetMapping("/status")
    @SaCheckLogin
    @ApiOperation(value = "查询支付状态", notes = "", httpMethod = "GET")
    public Integer queryPayStatus(@RequestParam String orderNo) {
        return paymentService.queryPayStatus(orderNo);
    }

}