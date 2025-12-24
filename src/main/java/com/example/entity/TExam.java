package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName t_exam
 */
@ApiModel(value="com-example-entity-tExam")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value ="t_exam")
public class TExam implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "exam_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="主键")
    private String examId;

    /**
     * 求职者ID
     */
    @TableField(value = "employee_user_id")
    @ApiModelProperty(value="求职者ID")
    private String employeeUserId;

    /**
     * 招聘者ID
     */
    @TableField(value = "hr_user_id")
    @ApiModelProperty(value="招聘者ID")
    private String hrUserId;

    /**
     * 问答内容
     */
    @TableField(value = "content")
    @ApiModelProperty(value="content")
    private String content;

    /**
     * 问题序号
     */
    @TableField(value = "que_index")
    @ApiModelProperty(value="问题序号")
    private int queIndex;

    /**
     * 岗位ID
     */
    @TableField(value = "position_id")
    @ApiModelProperty(value="岗位ID")
    private String positionId;

    /**
     * 岗位ID
     */
    @TableField(value = "resume_id")
    @ApiModelProperty(value="简历ID")
    private String resumeId;

    /**
     * 创建时间（UTC时间，包含时分秒毫秒）
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间（UTC时间，包含时分秒毫秒）")
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}