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
* 订单表
* @TableName t_order
*/
@ApiModel(value="TOrder")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_order")
public class TOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("订单号")
    @TableField(value = "order_no")
    private String orderNo;

    @TableField(value = "user_id")
    @ApiModelProperty("用户ID")
    private String userId;

    @TableField(value = "total_amount")
    @ApiModelProperty("订单总金额")
    private BigDecimal totalAmount;

    @TableField(value = "status")
    @ApiModelProperty("订单状态：0-待支付，1-已支付，2-已取消")
    private Integer status;

    @ApiModelProperty("套餐ID")
    @TableField(value = "package_id")
    private String packageId;

    @ApiModelProperty("套餐名称")
    @TableField(value = "package_name")
    private String packageName;

    @TableField(value = "create_time")
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;


    @TableField(value = "update_time")
    @ApiModelProperty("更新时间")
    private LocalDateTime updateTime;

}
