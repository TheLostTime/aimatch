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
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 求职者基本信息表
    */
@ApiModel(value="TEmployee")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "t_employee")
public class TEmployee implements Serializable {
    /**
     * 用户id，与t_user表主键相同
     */
    @TableId(value = "user_id", type = IdType.INPUT)
    @ApiModelProperty(value="用户id，与t_user表主键相同")
    private String userId;

    /**
     * 姓名
     */
    @TableField(value = "name")
    @ApiModelProperty(value="姓名")
    private String name;

    /**
     * 性别，取值：man、woman
     */
    @TableField(value = "gender")
    @ApiModelProperty(value="性别，取值：man、woman")
    private String gender;

    /**
     * 身份，取值：0-职场人 1-学生
     */
    @TableField(value = "identity")
    @ApiModelProperty(value="身份，取值：0-职场人 1-学生")
    private Integer identity;

    /**
     * 出生日期，记录到月
     */
    @TableField(value = "birth_date")
    @ApiModelProperty(value="出生日期，记录到月")
    private String birthDate;

    /**
     * 年龄，根据出生日期自动计算
     */
    @TableField(value = "age")
    @ApiModelProperty(value="年龄，根据出生日期自动计算")
    private Integer age;

    /**
     * 现居住地址
     */
    @TableField(value = "residentia_address")
    @ApiModelProperty(value="现居住地址")
    private String residentiaAddress;

    /**
     * 参加工作日期，记录到月
     */
    @TableField(value = "start_work_date")
    @ApiModelProperty(value="参加工作日期，记录到月")
    private String startWorkDate;

    /**
     * 工作经验，单位：年，1<=经验<=99
     */
    @TableField(value = "work_experience")
    @ApiModelProperty(value="工作经验，单位：年，1<=经验<=99")
    private Integer workExperience;

    /**
     * 手机号，演示版本明文存储
     */
    @TableField(value = "mobile")
    @ApiModelProperty(value="手机号，演示版本明文存储")
    private String mobile;

    /**
     * 邮箱，演示版本明文存储
     */
    @TableField(value = "mail")
    @ApiModelProperty(value="邮箱，演示版本明文存储")
    private String mail;

    private static final long serialVersionUID = 1L;
}