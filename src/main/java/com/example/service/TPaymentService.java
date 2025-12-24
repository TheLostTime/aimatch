package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.TPayment;

import javax.servlet.http.HttpServletRequest;

/**
* @author wk794
* @description 针对表【t_payment(支付记录表)】的数据库操作Service
* @createDate 2025-10-13 15:47:14
*/
public interface TPaymentService extends IService<TPayment> {

    /**
     * 创建支付二维码
     */
    String createPayQrcode(String orderId, Integer channel,String remoteAddress);

    /**
     * 处理支付回调
     */
    String handleCallback(Integer channel, HttpServletRequest request);

    /**
     * 查询支付状态
     */
    Integer queryPayStatus(String orderNo);
}
