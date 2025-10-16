package com.example.util;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AlipayConfig {
    // 支付宝appid
    private String appId;
    // 商户私钥
    private String privateKey;
    // 支付宝公钥
    private String publicKey;
    // 回调地址
    private String notifyUrl;
    // 是否启用沙箱环境
    private boolean sandbox = true;
}
