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

@ApiModel(value="com-example-entity-THr")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "t_hr")
public class THr implements Serializable {
    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.INPUT)
    @ApiModelProperty(value="用户id")
    private String userId;

    /**
     * 名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value="名称")
    private String name;

    /**
     * 职务/头衔
     */
    @TableField(value = "title")
    @ApiModelProperty(value="职务/头衔")
    private String title;

    /**
     * 所属公司id
     */
    @TableField(value = "company_id")
    @ApiModelProperty(value="所属公司id")
    private String companyId;

    /**
     * NO-非vip、NORMAL--普通vip、HIGH-豪华vip
     */
    @TableField(value = "vip_type")
    @ApiModelProperty(value="NO-非vip、NORMAL--普通vip、HIGH-豪华vip")
    private String vipType;

    @TableField(value = "real_name")
    @ApiModelProperty(value="是否实名认证1是2否")
    private Integer realName;

    private static final long serialVersionUID = 1L;
}