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
import lombok.NoArgsConstructor;

/**
    * 岗位工具箱表
    */
@ApiModel(value="com-example-entity-TPositionToolbox")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "t_position_toolbox")
public class TPositionToolbox implements Serializable {
    /**
     * 岗位id，主键
     */
    @TableId(value = "position_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="岗位id，主键")
    private String positionId;

    /**
     * AI笔试官开启标志，0-关闭 1-开启
     */
    @TableField(value = "ai_examiner")
    @ApiModelProperty(value="AI笔试官开启标志，0-关闭 1-开启")
    private Integer aiExaminer;

    /**
     * AI笔试要求
     */
    @TableField(value = "ai_examiner_desc")
    @ApiModelProperty(value="AI笔试要求")
    private String aiExaminerDesc;

    /**
     * 出海提醒开启标志，0-关闭 1-开启
     */
    @TableField(value = "oversea_remainder")
    @ApiModelProperty(value="出海提醒开启标志，0-关闭 1-开启")
    private Integer overseaRemainder;

    /**
     * 简历投递门槛设置标识，0-未设置 1-已设置
     */
    @TableField(value = "resume_threshold")
    @ApiModelProperty(value="简历投递门槛设置标识，0-未设置 1-已设置")
    private Integer resumeThreshold;

    /**
     * 最低学历，0-不限 1-初中及以下 2-高中 3-中专/中技 4-大专 5-本科 6-硕士 7-MBA/EMBA 8-博士
     */
    @TableField(value = "degree_lower_limit")
    @ApiModelProperty(value="最低学历，0-不限 1-初中及以下 2-高中 3-中专/中技 4-大专 5-本科 6-硕士 7-MBA/EMBA 8-博士")
    private Integer degreeLowerLimit;

    /**
     * 最低工作经验，0-经验不限 1-无经验 2-1年以下 3-1-3年 4-3-5年 5-5-10年 6-10年以上
     */
    @TableField(value = "experience_lower_limit")
    @ApiModelProperty(value="最低工作经验，0-经验不限 1-无经验 2-1年以下 3-1-3年 4-3-5年 5-5-10年 6-10年以上")
    private Integer experienceLowerLimit;

    /**
     * 最高工作经验，0-无限制 1-1年以下 2-1-3年 3-3-5年 4-5-10年 5-10年以上
     */
    @TableField(value = "experience_upper_limit")
    @ApiModelProperty(value="最高工作经验，0-无限制 1-1年以下 2-1-3年 3-3-5年 4-5-10年 5-10年以上")
    private Integer experienceUpperLimit;

    /**
     * 年龄下限，18-55表示具体年龄，56-55岁以上
     */
    @TableField(value = "age_lower_limit")
    @ApiModelProperty(value="年龄下限，18-55表示具体年龄，56-55岁以上")
    private Integer ageLowerLimit;

    /**
     * 年龄上限，18-55表示具体年龄，56-55岁以上
     */
    @TableField(value = "age_upper_limit")
    @ApiModelProperty(value="年龄上限，18-55表示具体年龄，56-55岁以上")
    private Integer ageUpperLimit;

    /**
     * 院校要求，all-不限
     */
    @TableField(value = "college")
    @ApiModelProperty(value="院校要求，all-不限")
    private String college;

    /**
     * 专业要求，all-不限
     */
    @TableField(value = "major")
    @ApiModelProperty(value="专业要求，all-不限")
    private String major;

    /**
     * 性别要求，0-all 1-man 2-woman
     */
    @TableField(value = "gender")
    @ApiModelProperty(value="性别要求，0-all 1-man 2-woman")
    private Integer gender;

    /**
     * 居住地址要求，all-不限
     */
    @TableField(value = "residentia_address")
    @ApiModelProperty(value="居住地址要求，all-不限")
    private String residentiaAddress;

    private static final long serialVersionUID = 1L;
}