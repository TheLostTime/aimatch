package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
    * 求职者教育经历表
    */
@ApiModel(value="com-example-entity-TResumeEducation")
@Data
@AllArgsConstructor
@TableName(value = "t_resume_education")
public class TResumeEducation implements Serializable {
    /**
     * 教育经历id
     */
    @TableId(value = "education_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="教育经历id")
    private String educationId;

    /**
     * 排列顺序，从1开始递增
     */
    @TableField(value = "education_index")
    @ApiModelProperty(value="排列顺序，从1开始递增")
    private Integer educationIndex;

    /**
     * 关联的简历id
     */
    @TableField(value = "resume_id")
    @ApiModelProperty(value="关联的简历id")
    private String resumeId;

    /**
     * 学历，取值：不限、初中及以下、高中、中专/中技、大专、本科、硕士、MBA/EMBA、博士
     */
    @TableField(value = "degree")
    @ApiModelProperty(value="学历，取值：不限、初中及以下、高中、中专/中技、大专、本科、硕士、MBA/EMBA、博士")
    private String degree;

    /**
     * 学校名称
     */
    @TableField(value = "college")
    @ApiModelProperty(value="学校名称")
    private String college;

    /**
     * 专业
     */
    @TableField(value = "major")
    @ApiModelProperty(value="专业")
    private String major;

    /**
     * 在校开始时间，UTC时间（年月）
     */
    @TableField(value = "begin_date")
    @ApiModelProperty(value="在校开始时间，UTC时间（年月）")
    private String beginDate;

    /**
     * 在校结束时间，UTC时间（年月）
     */
    @TableField(value = "end_date")
    @ApiModelProperty(value="在校结束时间，UTC时间（年月）")
    private String endDate;

    private static final long serialVersionUID = 1L;
}