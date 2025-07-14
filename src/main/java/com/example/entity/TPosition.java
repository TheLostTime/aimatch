package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="TPosition")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "t_position")
public class TPosition implements Serializable {

    /**
     * 岗位id
     */
    @TableId(value = "position_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="岗位id")
    private String positionId;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户id")
    private String userId;


    /**
     * 岗位类型0-HR岗位1-内推岗位
     */
    @TableField(value = "position_type")
    @ApiModelProperty(value="岗位类型0-HR岗位1-内推岗位")
    private String positionType;

    /**
     * 岗位发布状态0-草稿1-审核中2-在线3-驳回
     */
    @TableField(value = "position_status")
    @ApiModelProperty(value="岗位发布状态0-草稿(下线变草稿)1-审核中2-在线3-驳回")
    private Integer positionStatus;

    /**
     * 工作性质全职full_time兼职part_time实习internship应届校园招聘fresh_campus
     */
    @TableField(value = "work_type")
    @ApiModelProperty(value="工作性质全职full_time兼职part_time实习internship应届校园招聘fresh_campus")
    private String workType;

    /**
     * 岗位名称
     */
    @TableField(value = "position_name")
    @ApiModelProperty(value="岗位名称")
    private String positionName;

    /**
     * 岗位描述
     */
    @TableField(value = "position_description")
    @ApiModelProperty(value="岗位描述")
    private String positionDescription;

    /**
     * 职位类别
     */
    @TableField(value = "job_category")
    @ApiModelProperty(value="职位类别")
    private String jobCategory;

    /**
     * 最低学历：不限初中及以下高中中专/中技大专本科硕士MBA/EMBA博士(从0-9)

     */
    @TableField(value = "degree_lower_limit")
    @ApiModelProperty(value="最低学历：不限初中及以下高中中专/中技大专本科硕士MBA/EMBA博士(从0-9),")
    private Integer degreeLowerLimit;

    /**
     * 最低经验：经验不限无经验1年以下1-3年3-5年5-10年10年以上(从0-7)
     */
    @TableField(value = "experience_lower_limit")
    @ApiModelProperty(value="最低经验：经验不限无经验1年以下1-3年3-5年5-10年10年以上(从0-7)")
    private Integer experienceLowerLimit;

    /**
     * 薪水下限单位k，最低是1
     */
    @TableField(value = "salary_lower_limit")
    @ApiModelProperty(value="薪水下限单位k，最低是1")
    private Integer salaryLowerLimit;

    /**
     * 薪水上限单位k，最低是1
     */
    @TableField(value = "salary_upper_limit")
    @ApiModelProperty(value="薪水上限单位k，最低是1")
    private Integer salaryUpperLimit;

    /**
     * XX薪(12个月
13个月
...
24个月
)
     */
    @TableField(value = "salary_month")
    @ApiModelProperty(value="XX薪(12个月,13个月,...,24个月,)")
    private Integer salaryMonth;

    /**
     * 工作地址
     */
    @TableField(value = "work_place")
    @ApiModelProperty(value="工作地址")
    private String workPlace;

    /**
     * 是否海外岗位1 是 2 否
     */
    @TableField(value = "is_oversea")
    @ApiModelProperty(value="是否海外岗位1 是 2 否")
    private Integer isOversea;

    /**
     * 是否开启出海提醒1 是 2 否
     */
    @TableField(value = "oversea_remainder")
    @ApiModelProperty(value="是否开启出海提醒1 是 2 否")
    private Integer overseaRemainder;

    /**
     * 招聘人数不超过99999
     */
    @TableField(value = "position_num")
    @ApiModelProperty(value="招聘人数不超过99999")
    private Integer positionNum;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="更新时间-自动下线依据")
    private Date updateTime;

    @TableField(value = "reason")
    @ApiModelProperty(value="驳回理由")
    private String reason;

    private static final long serialVersionUID = 1L;
}