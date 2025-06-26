package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
    * 求职者VIP状态表
    */
@ApiModel(value="com-example-entity-TEmployeeVip")
@Data
@AllArgsConstructor
@TableName(value = "t_employee_vip")
public class TEmployeeVip implements Serializable {
    /**
     * 用户id，与t_user表主键相同
     */
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="用户id，与t_user表主键相同")
    private String userId;

    /**
     * VIP类型，取值：NO-非vip、NORMAL-普通vip、EXPIRED-已过期
     */
    @TableField(value = "vip_type")
    @ApiModelProperty(value="VIP类型，取值：NO-非vip、NORMAL-普通vip、EXPIRED-已过期")
    private String vipType;

    /**
     * VIP权益开始时间，UTC时间
     */
    @TableField(value = "begin_time")
    @ApiModelProperty(value="VIP权益开始时间，UTC时间")
    private Date beginTime;

    /**
     * VIP权益结束时间，UTC时间
     */
    @TableField(value = "expire_time")
    @ApiModelProperty(value="VIP权益结束时间，UTC时间")
    private Date expireTime;

    private static final long serialVersionUID = 1L;
}