package com.example.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.alipay.easysdk.factory.Factory;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.constant.PayConstant;
import com.example.entity.*;
import com.example.exception.BusinessException;
import com.example.mapper.TOrderMapper;
import com.example.mapper.TPaymentMapper;
import com.example.mapper.TVipPackageMapper;
import com.example.service.*;
import com.example.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.constant.Constants.VIP_HIGH;
import static com.example.constant.Constants.VIP_NORMAL;

/**
* @author wk794
* @description 针对表【t_payment(支付记录表)】的数据库操作Service实现
* @createDate 2025-10-13 15:47:14
*/
@Service
@Slf4j
public class TPaymentServiceImpl extends ServiceImpl<TPaymentMapper, TPayment>
    implements TPaymentService{
    @Autowired
    private TOrderMapper orderMapper;

    @Autowired
    private WxPayUtil wxPayUtil;

    @Autowired
    private AlipayUtil alipayUtil;

    @Autowired
    private WxPayConfig wxPayConfig;

    @Autowired
    private AlipayConfig alipayConfig;

    @Autowired
    private TCompanyService companyService;

    @Autowired
    private THrVipService tHrVipService;

    @Autowired
    private THrService tHrService;

    @Autowired
    private TVipPackageMapper tVipPackageMapper;

    @Autowired
    private THrPaidPermisionsService tHrPaidPermisionsService;

    @Autowired
    private THrPaidPermisionsUseDetailService tHrPaidPermisionsUseDetailService;

    @Override
    public String createPayQrcode(String orderId, Integer channel) {
        TOrder order = orderMapper.selectById(orderId);
        if (order == null || order.getStatus() != PayConstant.ORDER_STATUS_PENDING) {
            throw new BusinessException(10000,"订单状态异常");
        }
        // 查看该订单是否存在的支付记录
        TPayment payment = baseMapper.selectOne(new LambdaQueryWrapper<TPayment>().eq(TPayment::getOrderId, orderId));
        if (null != payment) {
            // 如果支付状态是成功的则无需继续支付，否则继续生成支付二维码
            if (payment.getStatus() == PayConstant.PAY_STATUS_SUCCESS) {
                throw new BusinessException(10000,"该订单已支付成功无需再生成二维码支付");
            }
        } else {
            String payNo = "PAY" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            payment = new TPayment();
            payment.setOrderId(orderId);
            payment.setPayNo(payNo);
            payment.setChannel(channel);
            payment.setAmount(order.getTotalAmount());
            payment.setStatus(PayConstant.PAY_STATUS_PROCESSING);
            payment.setCreateTime(LocalDateTime.now());
            payment.setUpdateTime(LocalDateTime.now());
            baseMapper.insert(payment);
        }
        try {
            String qrcodeUrl;
            if (channel == PayConstant.CHANNEL_WECHAT) {
                qrcodeUrl = wxPayUtil.createQrcode(order.getOrderNo(), order.getTotalAmount().doubleValue());
                payment.setQrcodeUrl(qrcodeUrl);
                baseMapper.updateById(payment);
                // 生成二维码图片返回给前端
                byte[] imageData = QrcodeUtil.createQrcodeImage(qrcodeUrl, 300, 300);
                return "data:image/png;base64," + java.util.Base64.getEncoder().encodeToString(imageData);
            } else if (channel == PayConstant.CHANNEL_ALIPAY) {
                // 支付宝直接返回表单
                return alipayUtil.createQrcode(order.getOrderNo(), order.getTotalAmount().doubleValue());
            }
        } catch (Exception e) {
            throw new BusinessException(10001,"生成二维码失败", e);
        }
        return null;
    }

    @Override
    public String handleCallback(Integer channel, HttpServletRequest request) {
        try {
            if (channel == PayConstant.CHANNEL_WECHAT) {
                return handleWxCallback(request);
            } else if (channel == PayConstant.CHANNEL_ALIPAY) {
                return handleAlipayCallback(request);
            }
        } catch (Exception e) {
            log.error("支付回调处理失败", e);
            return "fail";
        }
        return "success";
    }

    private String handleWxCallback(HttpServletRequest request) throws Exception {
        // 1. 获取微信回调参数
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String xmlData = sb.toString();
        log.info("微信支付回调数据: {}", xmlData);

        // 2. 解析XML
        Map<String, String> respData = WXPayUtil.xmlToMap(xmlData);

        // 3. 验签
        boolean signValid = WXPayUtil.isSignatureValid(respData, wxPayConfig.getMchKey());
        if (!signValid) {
            log.error("微信回调签名验证失败");
            return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[签名失败]]></return_msg></xml>";
        }

        // 4. 判断支付结果
        if ("SUCCESS".equals(respData.get("return_code")) && "SUCCESS".equals(respData.get("result_code"))) {
            String orderNo = respData.get("out_trade_no");
            TOrder order = orderMapper.selectOne(new LambdaQueryWrapper<TOrder>().eq(TOrder::getOrderNo, orderNo));
            if (order != null && order.getStatus() == PayConstant.ORDER_STATUS_PENDING) {
                // 更新订单状态
                order.setStatus(PayConstant.ORDER_STATUS_PAID);
                order.setUpdateTime(LocalDateTime.now());
                orderMapper.updateById(order);

                // 更新支付记录
                TPayment payment = baseMapper.selectOne(new LambdaQueryWrapper<TPayment>()
                        .eq(TPayment::getOrderId, order.getId()));
                if (payment != null) {
                    payment.setStatus(PayConstant.PAY_STATUS_SUCCESS);
                    payment.setCallbackData(xmlData);
                    payment.setUpdateTime(LocalDateTime.now());
                    baseMapper.updateById(payment);
                }
                // 更新用户权限
                handleCallbackPermission(orderNo);
            }
        }

        // 5. 返回成功给微信
        return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }

    private String handleAlipayCallback(HttpServletRequest request) throws Exception {
        // 1. 获取支付宝回调参数
        Map<String, String> params = getParams(request);
        log.info("支付宝支付回调数据: {}", params);

        // 2. 验签
        boolean signValid = Factory.Payment.Common().verifyNotify(params);
        if (!signValid) {
            log.error("支付宝回调签名验证失败");
            return "fail";
        }

        // 3. 判断支付结果
        if ("TRADE_SUCCESS".equals(params.get("trade_status"))) {
            String orderNo = params.get("out_trade_no");
            TOrder order = orderMapper.selectOne(new LambdaQueryWrapper<TOrder>().eq(TOrder::getOrderNo, orderNo));
            if (order != null && order.getStatus() == PayConstant.ORDER_STATUS_PENDING) {
                // 更新订单状态
                order.setStatus(PayConstant.ORDER_STATUS_PAID);
                order.setUpdateTime(LocalDateTime.now());
                orderMapper.updateById(order);

                // 更新支付记录
                TPayment payment = baseMapper.selectOne(new LambdaQueryWrapper<TPayment>()
                        .eq(TPayment::getOrderId, order.getId()));
                if (payment != null) {
                    payment.setStatus(PayConstant.PAY_STATUS_SUCCESS);
                    payment.setCallbackData(new ObjectMapper().writeValueAsString(params));
                    payment.setUpdateTime(LocalDateTime.now());
                    baseMapper.updateById(payment);
                }
                // 更新用户权限
                handleCallbackPermission(orderNo);
            }
        }
        // 4. 返回成功给支付宝
        return "success";
    }


    /**
     * 支付成功后更新使用表的数量（在原来的基础上累加）
     * @return
     */
    private String handleCallbackPermission(String orderNo) {
        // 根据订单号获取订单继而获取买家ID
        TOrder order = orderMapper.selectOne(new LambdaQueryWrapper<TOrder>().eq(TOrder::getOrderNo, orderNo));
        if (order == null) {
            throw new BusinessException(10001,"履行订单时，查询到订单不存在，订单号:" + orderNo);
        }
        String userId = order.getUserId();
        // 1.激活vip(已经不存在会员了，为了保持尽可能小的改动，设置为普通会员)
        Date currentDate = DateUtil.date();
        THrVip tHrVip = THrVip.builder()
                .vipType(VIP_NORMAL)
                .userId(userId)
                .beginTime(currentDate)
                .expireTime(currentDate) // 无过期
                .build();
        tHrVipService.saveOrUpdate(tHrVip);

        // 将t_hr表vip_type字段更新为hrActivateReq.getVipType()
        tHrService.updateById(THr.builder()
                .vipType(VIP_NORMAL)
                .userId(userId)
                .build()
        );

        // 2.根据packageId查询套餐规格
        TVipPackage tVipPackage = tVipPackageMapper.selectById(order.getPackageId());
        if (null == tVipPackage) {
            throw new BusinessException(10012,"套餐不存在");
        }
        // 3.开通权限(购买的历史权益表可能存在多条)
        THrPaidPermisions tHrPaidPermisions = THrPaidPermisions.builder()
                .aiGenerate(tVipPackage.getAiGenerate())
                .downloadNum(tVipPackage.getDownloadNum())
                .positionNum(tVipPackage.getPositionNum())
                .sayHello(tVipPackage.getSayHello())
                .viewResume(tVipPackage.getViewResume())
                .userId(userId)
                .orderId(order.getId())
                .build();
        tHrPaidPermisionsService.save(tHrPaidPermisions);

        // 4.初始化权限使用情况(如果是第一次购买则新增，如果不是则累加，一个用户只有一条数据)
        THrPaidPermisionsUseDetail tHrPaidPermisionsUseDetail = tHrPaidPermisionsUseDetailService.getById(userId);
        if (null == tHrPaidPermisionsUseDetail) {
            tHrPaidPermisionsUseDetail = THrPaidPermisionsUseDetail.builder()
                    .userId(StpUtil.getLoginId().toString())
                    .usedPositionNum(tVipPackage.getPositionNum())
                    .usedViewResume(tVipPackage.getViewResume())
                    .usedSayHello(tVipPackage.getSayHello())
                    .usedDownloadNum(tVipPackage.getDownloadNum())
                    .createTime(currentDate)
                    .updateTime(currentDate)
                    .build();
            tHrPaidPermisionsUseDetailService.save(tHrPaidPermisionsUseDetail);
        } else {
            tHrPaidPermisionsUseDetail.setUsedPositionNum(tHrPaidPermisionsUseDetail.getUsedPositionNum()
                    + tVipPackage.getPositionNum());
            tHrPaidPermisionsUseDetail.setUsedViewResume(tHrPaidPermisionsUseDetail.getUsedViewResume()
                    + tVipPackage.getViewResume());
            tHrPaidPermisionsUseDetail.setUsedSayHello(tHrPaidPermisionsUseDetail.getUsedSayHello()
                    + tVipPackage.getSayHello());
            tHrPaidPermisionsUseDetail.setUsedDownloadNum(tHrPaidPermisionsUseDetail.getUsedDownloadNum()
                    + tVipPackage.getDownloadNum());
            tHrPaidPermisionsUseDetail.setUpdateTime(currentDate);
            tHrPaidPermisionsUseDetailService.updateById(tHrPaidPermisionsUseDetail);
        }
        return "success";
    }

    private Map<String, String> getParams(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        request.getParameterMap().forEach((key, values) -> {
            params.put(key, values[0]);
        });
        return params;
    }


    @Override
    public Integer queryPayStatus(String orderNo) {
        TPayment payment = baseMapper.selectOne(
                new QueryWrapper<TPayment>()
                        .eq("order_id", orderMapper.selectOne(
                                new QueryWrapper<TOrder>().eq("order_no", orderNo)).getId())
        );
        return payment != null ? payment.getStatus() : PayConstant.PAY_STATUS_FAILED;
    }
}




