package com.example.util;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;
import com.example.exception.BusinessException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class AlipayUtil {

    private final AlipayConfig alipayConfig;

    public AlipayUtil(AlipayConfig alipayConfig) {
        this.alipayConfig = alipayConfig;
    }

    @PostConstruct
    public void init() {
        com.alipay.easysdk.kernel.Config config = new com.alipay.easysdk.kernel.Config();
        config.protocol = "https";
        config.gatewayHost = alipayConfig.isSandbox() ? "openapi-sandbox.dl.alipaydev.com" : "openapi.alipay.com";
        config.signType = "RSA2";
        config.appId = alipayConfig.getAppId();
        config.merchantPrivateKey = alipayConfig.getPrivateKey();
        config.alipayPublicKey = alipayConfig.getPublicKey();
        config.notifyUrl = alipayConfig.getNotifyUrl();
        Factory.setOptions(config);
    }

    public String createQrcode(String orderNo, double totalAmount) {
        try {
            // 1. 调用支付宝预下单接口（生成二维码链接）
            AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace()
                    .preCreate("订单-" + orderNo,   // 订单标题
                            orderNo,             // 商户订单号
                            String.valueOf(totalAmount)); // 订单金额

            // 2. 检查返回是否成功
            if (response.getCode() != null && "10000".equals(response.getCode())) {
                String qrCodeUrl = response.getQrCode(); // 支付宝返回的二维码字符串
                log.info("支付宝预下单成功，qrCodeUrl: {}", qrCodeUrl);

                // 3. 将二维码字符串转成 base64 图片
                int width = 300;
                int height = 300;
                String format = "png";
                Map<EncodeHintType, Object> hints = new HashMap<>();
                hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
                hints.put(EncodeHintType.MARGIN, 1);

                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeUrl, BarcodeFormat.QR_CODE, width, height, hints);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                MatrixToImageWriter.writeToStream(bitMatrix, format, outputStream);

                // 4. 转成 base64
                String base64Image = "data:image/png;base64," + Base64.getEncoder().encodeToString(outputStream.toByteArray());
                return base64Image;
            } else {
                throw new BusinessException(10001,"支付宝获取二维码失败: " + response.getMsg() + "，" + response.getSubMsg());
            }
        }catch (com.aliyun.tea.TeaException e){
            throw new BusinessException(10001,"支付宝获取二维码失败:" + e.getCode() + e.getMessage(), e);
        }catch (Exception e) {
            throw new BusinessException(10001,"支付宝获取二维码失败",e);
        }
    }
}
