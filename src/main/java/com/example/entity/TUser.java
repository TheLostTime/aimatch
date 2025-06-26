package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@ApiModel(value="TUser")
@Data
@AllArgsConstructor
@TableName(value = "t_user")
public class TUser implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="主键")
    private String userId;

    /**
     * 0-求职者1-HR2-内推人3-运营管理员
     */
    @TableField(value = "user_type")
    @ApiModelProperty(value="0-求职者1-HR2-内推人3-运营管理员")
    @NotNull(message = "userType不能为空")
    private String userType;

    /**
     * 用户名
     */
    @TableField(value = "account")
    @ApiModelProperty(value="用户名")
    private String account;

    /**
     * 密码
     */
    @TableField(value = "password")
    @ApiModelProperty(value="密码")
    private String password;

    /**
     * 头像(二进制文件转字符串存储)
     */
    @TableField(value = "avatar")
    @ApiModelProperty(value="头像(二进制文件转字符串存储)")
    private String avatar;

    private static final long serialVersionUID = 1L;
}