package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
    * 求职者求职期望表
    */
@ApiModel(value="com-example-entity-TResumeInterestJob")
@Data
@AllArgsConstructor
@TableName(value = "t_resume_interest_job")
public class TResumeInterestJob implements Serializable {
    /**
     * 求职意向id
     */
    @TableId(value = "interest_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="求职意向id")
    private String interestId;

    /**
     * 排列顺序，从1开始递增
     */
    @TableField(value = "interest_index")
    @ApiModelProperty(value="排列顺序，从1开始递增")
    private Integer interestIndex;

    /**
     * 关联的简历id
     */
    @TableField(value = "resume_id")
    @ApiModelProperty(value="关联的简历id")
    private String resumeId;

    /**
     * 期望行业，与HR岗位类别对应
     */
    @TableField(value = "job_category")
    @ApiModelProperty(value="期望行业，与HR岗位类别对应")
    private String jobCategory;

    /**
     * 期望职位
     */
    @TableField(value = "position_name")
    @ApiModelProperty(value="期望职位")
    private String positionName;

    /**
     * 期望工作城市
     */
    @TableField(value = "city")
    @ApiModelProperty(value="期望工作城市")
    private String city;

    /**
     * 薪水下限，单位：千（如20代表20K）
     */
    @TableField(value = "salary_lower_limit")
    @ApiModelProperty(value="薪水下限，单位：千（如20代表20K）")
    private Integer salaryLowerLimit;

    /**
     * 薪水上限，单位：千
     */
    @TableField(value = "salary_upper_limit")
    @ApiModelProperty(value="薪水上限，单位：千")
    private Integer salaryUpperLimit;

    /**
     * XX薪，取值：12、13...24
     */
    @TableField(value = "salary_month")
    @ApiModelProperty(value="XX薪，取值：12、13...24")
    private Integer salaryMonth;

    private static final long serialVersionUID = 1L;
}