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
    * 求职者工作经历表
    */
@ApiModel(value="com-example-entity-TResumeWork")
@Data
@AllArgsConstructor
@TableName(value = "t_resume_work")
public class TResumeWork implements Serializable {

    /**
     * 工作id
     */
    @TableId(value = "work_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="工作id")
    private String workId;

    /**
     * 排列顺序，从1开始递增
     */
    @TableField(value = "work_index")
    @ApiModelProperty(value="排列顺序，从1开始递增")
    private Integer workIndex;

    /**
     * 关联的简历id
     */
    @TableField(value = "resume_id")
    @ApiModelProperty(value="关联的简历id")
    private String resumeId;

    /**
     * 职位名称
     */
    @TableField(value = "position_name")
    @ApiModelProperty(value="职位名称")
    private String positionName;

    /**
     * 公司名称
     */
    @TableField(value = "company_name")
    @ApiModelProperty(value="公司名称")
    private String companyName;

    /**
     * 工作开始时间，UTC时间（年月）
     */
    @TableField(value = "begin_date")
    @ApiModelProperty(value="工作开始时间，UTC时间（年月）")
    private String beginDate;

    /**
     * 工作结束时间，UTC时间（年月）
     */
    @TableField(value = "end_date")
    @ApiModelProperty(value="工作结束时间，UTC时间（年月）")
    private String endDate;

    /**
     * 工作描述
     */
    @TableField(value = "work_desc")
    @ApiModelProperty(value="工作描述")
    private String workDesc;

    private static final long serialVersionUID = 1L;
}