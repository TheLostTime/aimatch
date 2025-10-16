package com.example.constant;

public interface PayConstant {

    // 微信支付
    public static final int CHANNEL_WECHAT = 1;

    // 支付宝支付
    public static final int CHANNEL_ALIPAY = 2;

    // 支付状态-处理中
    public static final int PAY_STATUS_PROCESSING = 0;
    // 支付状态-成功
    public static final int PAY_STATUS_SUCCESS = 1;
    // 支付状态-失败
    public static final int PAY_STATUS_FAILED = 2;

    // 订单状态-待支付
    public static final int ORDER_STATUS_PENDING = 0;
    // 订单状态-已支付
    public static final int ORDER_STATUS_PAID = 1;
    // 订单状态-已取消
    public static final int ORDER_STATUS_CANCELED = 2;
}
