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
import lombok.NoArgsConstructor;

/**
    * 求职者附件简历存储表
    */
@ApiModel(value="com-example-entity-TEmployeeResumeFile")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "t_employee_resume_file")
public class TEmployeeResumeFile implements Serializable {
    /**
     * 文件id，文件存储系统返回的唯一标识
     */
    @TableId(value = "file_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="文件id，文件存储系统返回的唯一标识")
    private String fileId;

    /**
     * 文件名称
     */
    @TableField(value = "file_name")
    @ApiModelProperty(value="文件名称")
    private String fileName;

    /**
     * 文件在服务器上的绝对路径
     */
    @TableField(value = "path")
    @ApiModelProperty(value="文件在服务器上的绝对路径")
    private String path;

    /**
     * 用户id，与t_user表主键相同
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户id，与t_user表主键相同")
    private String userId;

    private static final long serialVersionUID = 1L;
}