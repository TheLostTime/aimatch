package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * VIP套餐表
    */
@ApiModel(value="TVipPackage")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "t_vip_package")
public class TVipPackage implements Serializable {
    /**
     * 套餐记录唯一ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="套餐记录唯一ID")
    private String id;

    /**
     * VIP类型，0-NO-非vip 1-NORMAL-普通vip 2-HIGH-豪华vip 3-EXPIRED-已过期
     */
    @TableField(value = "vip_type")
    @ApiModelProperty(value="VIP类型，0-NO-非vip 1-NORMAL-普通vip 2-HIGH-豪华vip 3-EXPIRED-已过期")
    private String vipType;

    /**
     * 30 60 90天
     */
    @TableField(value = "spec")
    @ApiModelProperty(value="30 60 90天")
    private Integer spec;

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
     * 套餐价格
     */
    @TableField(value = "price")
    @ApiModelProperty(value="套餐价格")
    private BigDecimal price;

    /**
     * 状态：1:上架 2:下架
     */
    @TableField(value = "status")
    @ApiModelProperty(value="状态：1:上架 2:下架")
    private Integer status;

    /**
     * 套餐名称
     */
    @TableField(value = "package_name")
    @ApiModelProperty(value="套餐名称")
    private String packageName;

    private static final long serialVersionUID = 1L;
}