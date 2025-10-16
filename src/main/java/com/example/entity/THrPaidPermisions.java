package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
    * HR付费权限总额度表
    */
@ApiModel(value="com-example-entity-THrPaidPermisions")
@Data
@AllArgsConstructor
@Builder
@TableName(value = "t_hr_paid_permisions")
public class THrPaidPermisions implements Serializable {
    /**
     * 用户id，主键，与t_user表主键相同
     */
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="用户id，主键，与t_user表主键相同")
    private String userId;

    /**
     * 竞招职位数量，AI高匹配简历推荐、AI笔试官、企业出海提醒的可用职位数量
     */
    @TableField(value = "position_num")
    @ApiModelProperty(value="竞招职位数量，AI高匹配简历推荐、AI笔试官、企业出海提醒的可用职位数量")
    private Integer positionNum;

    /**
     * 每日主动查看简历数量
     */
    @TableField(value = "view_resume")
    @ApiModelProperty(value="每日主动查看简历数量")
    private Integer viewResume;

    /**
     * 每日主动打招呼次数
     */
    @TableField(value = "say_hello")
    @ApiModelProperty(value="每日主动打招呼次数")
    private Integer sayHello;

    /**
     * 每月可下载简历数量
     */
    @TableField(value = "download_num")
    @ApiModelProperty(value="每月可下载简历数量")
    private Integer downloadNum;

    /**
     * AI生成职位描述开关，0-关闭 1-开启
     */
    @TableField(value = "ai_generate")
    @ApiModelProperty(value="AI生成职位描述开关，0-关闭 1-开启")
    private Integer aiGenerate;

    /**
     * 订单ID
     */
    @TableField(value = "order_id")
    @ApiModelProperty(value="订单ID")
    private String orderId;

    private static final long serialVersionUID = 1L;
}