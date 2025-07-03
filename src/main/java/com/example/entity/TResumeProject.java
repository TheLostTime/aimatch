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
    * 求职者项目经历表
    */
@ApiModel(value="com-example-entity-TResumeProject")
@Data
@AllArgsConstructor
@TableName(value = "t_resume_project")
public class TResumeProject implements Serializable {
    /**
     * 项目id
     */
    @TableId(value = "project_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="项目id")
    private String projectId;

    /**
     * 排列顺序，从1开始递增
     */
    @TableField(value = "project_index")
    @ApiModelProperty(value="排列顺序，从1开始递增")
    private Integer projectIndex;

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
     * 项目名称
     */
    @TableField(value = "project_name")
    @ApiModelProperty(value="项目名称")
    private String projectName;

    /**
     * 项目开始时间，UTC时间（年月）
     */
    @TableField(value = "begin_date")
    @ApiModelProperty(value="项目开始时间，UTC时间（年月）")
    private String beginDate;

    /**
     * 项目结束时间，UTC时间（年月）
     */
    @TableField(value = "end_date")
    @ApiModelProperty(value="项目结束时间，UTC时间（年月）")
    private String endDate;

    /**
     * 项目描述
     */
    @TableField(value = "project_desc")
    @ApiModelProperty(value="项目描述")
    private String projectDesc;

    /**
     * 项目业绩
     */
    @TableField(value = "project_achievements")
    @ApiModelProperty(value="项目业绩")
    private String projectAchievements;

    private static final long serialVersionUID = 1L;
}