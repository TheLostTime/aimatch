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
    * HR付费权限额度使用累计表
    */
@ApiModel(value="THrPaidPermisionsUseDetail")
@Data
@AllArgsConstructor
@Builder
@TableName(value = "t_hr_paid_permisions_use_detail")
public class THrPaidPermisionsUseDetail implements Serializable {
    /**
     * 用户id，主键，与t_user表主键相同
     */
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="用户id，主键，与t_user表主键相同")
    private String userId;

    /**
     * 已发布竞招职位数量，发布一个职位+1，下线一个职位-1
     */
    @TableField(value = "used_position_num")
    @ApiModelProperty(value="已发布竞招职位数量，发布一个职位+1，下线一个职位-1")
    private Integer usedPositionNum;

    /**
     * 每日主动查看简历数量
     */
    @TableField(value = "used_view_resume")
    @ApiModelProperty(value="每日主动查看简历数量")
    private Integer usedViewResume;

    /**
     * 每日主动打招呼次数
     */
    @TableField(value = "used_say_hello")
    @ApiModelProperty(value="每日主动打招呼次数")
    private Integer usedSayHello;

    /**
     * 每月可下载简历数量
     */
    @TableField(value = "used_download_num")
    @ApiModelProperty(value="每月可下载简历数量")
    private Integer usedDownloadNum;

    /**
     * 创建时间，UTC时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间，UTC时间")
    private Date createTime;

    /**
     * 更新时间，UTC时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="更新时间，UTC时间")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}