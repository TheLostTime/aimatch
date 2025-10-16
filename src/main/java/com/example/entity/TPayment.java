package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支付记录表
 * @TableName t_payment
 */
@ApiModel(value="TPayment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_payment")
public class TPayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("ID")
    private String id;

    /**
     * 订单ID
     */
    @TableField(value = "order_id")
    @ApiModelProperty("订单ID")
    private String orderId;

    /**
     * 支付流水号
     */
    @TableField(value = "pay_no")
    @ApiModelProperty("支付流水号")
    private String payNo;

    /**
     * 支付渠道：1-微信，2-支付宝
     */
    @TableField(value = "channel")
    @ApiModelProperty("支付渠道")
    private Integer channel;

    /**
     * 支付金额
     */
    @TableField(value = "amount")
    @ApiModelProperty("支付金额")
    private BigDecimal amount;

    /**
     * 支付状态：0-处理中，1-成功，2-失败
     */
    @TableField(value = "status")
    @ApiModelProperty("支付状态：0-处理中，1-成功，2-失败")
    private Integer status;

    /**
     * 支付二维码地址
     */
    @TableField(value = "qrcode_url")
    @ApiModelProperty("支付二维码地址")
    private String qrcodeUrl;

    /**
     * 支付回调数据
     */
    @TableField(value = "callback_data")
    @ApiModelProperty("支付回调数据")
    private String callbackData;


    @TableField(value = "create_time")
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;


    @TableField(value = "update_time")
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;


}