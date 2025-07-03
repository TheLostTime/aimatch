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
    * 求职者收藏职位表
    */
@ApiModel(value="com-example-entity-TEmployeeSaveJob")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "t_employee_save_job")
public class TEmployeeSaveJob implements Serializable {
    /**
     * 岗位id，与t_position表主键相同
     */
    @TableId(value = "position_id", type = IdType.INPUT)
    @ApiModelProperty(value="岗位id，与t_position表主键相同")
    private String positionId;

    /**
     * 求职者用户id
     */
    @ApiModelProperty(value="求职者用户id")
    @TableField(value = "employee_user_id")
    private String employeeUserId;

    /**
     * 已收藏标志
     */
    @TableField(value = "save_flag")
    @ApiModelProperty(value="已收藏标志")
    private Boolean saveFlag;

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