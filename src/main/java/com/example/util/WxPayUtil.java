package com.example.util;

import com.example.exception.BusinessException;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class WxPayUtil {
    private final WxPayConfig wxPayConfig;

    public WxPayUtil(WxPayConfig wxPayConfig) {
        this.wxPayConfig = wxPayConfig;
    }

    public String createQrcode(String orderNo, double totalAmount) {
        WXPay wxPay = new WXPay(new WXPayConfig() {
            @Override
            public String getAppID() {
                return wxPayConfig.getAppId();
            }
            @Override
            public String getMchID() {
                return wxPayConfig.getMchId();
            }
            @Override
            public String getKey() {
                return wxPayConfig.getMchKey();
            }
            @Override
            public InputStream getCertStream() { return null; }
            @Override
            public int getHttpConnectTimeoutMs() { return 8000; }
            @Override
            public int getHttpReadTimeoutMs() { return 10000; }
        },WXPayConstants.SignType.MD5,wxPayConfig.isSandbox());

        Map<String, String> data = new HashMap<>();
        data.put("body", "订单-" + orderNo);
        data.put("out_trade_no", orderNo);
        data.put("total_fee", String.valueOf((int)(totalAmount * 100))); // 单位分
        data.put("spbill_create_ip", getServerIp());
        data.put("notify_url", wxPayConfig.getNotifyUrl());
        data.put("trade_type", "NATIVE");
        Map<String, String> resp;
        try {
            // 发起统一下单请求
            resp = wxPay.unifiedOrder(data);
        } catch (Exception e) {
            throw new BusinessException(10001, "微信下单失败");
        }
        if ("SUCCESS".equals(resp.get("return_code")) && "SUCCESS".equals(resp.get("result_code"))) {
            return resp.get("code_url");
        } else {
            throw new RuntimeException("微信下单失败: " + resp.get("err_code_des"));
        }
    }

    public static String getServerIp() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    // 排除 IPv6 和回环地址
                    if (addr instanceof Inet4Address && !addr.isLoopbackAddress()) {
                        return addr.getHostAddress();
                    }
                }
            }
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
           log.error("获取服务器IP地址失败", e);
           return "127.0.0.1";
        }
    }
}
