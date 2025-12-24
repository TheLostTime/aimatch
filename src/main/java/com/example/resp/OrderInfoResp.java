package com.example.resp;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ApiModel(value="OrderInfoResp")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfoResp {

    @ApiModelProperty(value="订单ID")
    private String id;

    @ApiModelProperty(value="订单号")
    private String orderNo;

    @ApiModelProperty(value="总金额")
    private BigDecimal totalAmount;

    @ApiModelProperty(value="支付金额")
    private BigDecimal amount;

    @ApiModelProperty(value="支付方式")
    private Integer channel;

    @ApiModelProperty(value="订单状态：0-待支付，1-已支付，2-已取消")
    private Integer status;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("套餐ID")
    private String packageId;

    @ApiModelProperty("套餐名称")
    private String packageName;

}
