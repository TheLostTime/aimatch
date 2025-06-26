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
import lombok.Builder;
import lombok.Data;

/**
    * HR开通VIP表
    */
@ApiModel(value="com-example-entity-THrVip")
@Data
@AllArgsConstructor
@Builder
@TableName(value = "t_hr_vip")
public class THrVip implements Serializable {
    /**
     * 用户id，主键，与t_user表主键相同
     */
    @TableId(value = "user_id", type = IdType.INPUT)
    @ApiModelProperty(value="用户id，主键，与t_user表主键相同")
    private String userId;

    /**
     * VIP类型，0-NO-非vip 1-NORMAL-普通vip 2-HIGH-豪华vip 3-EXPIRED-已过期
     */
    @TableField(value = "vip_type")
    @ApiModelProperty(value="VIP类型，0-NO-非vip 1-NORMAL-普通vip 2-HIGH-豪华vip 3-EXPIRED-已过期")
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