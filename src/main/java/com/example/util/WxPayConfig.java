package com.example.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wx.pay")
public class WxPayConfig {
    // appid
    private String appId;
    // 商户号
    private String mchId;
    // 商户密钥
    private String mchKey;
    // 回调地址
    private String notifyUrl;
    // 是否启用沙箱环境
    private boolean sandbox = true;
}
