package com.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
    * 聊天记录表，用于存储HR与求职者的聊天消息
    */
@ApiModel(value="com-example-entity-TImMessage")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "t_im_message")
public class TImMessage implements Serializable {
    /**
     * 岗位id，与t_position岗位表主键相同
     */
    @TableField(value = "position_id")
    @ApiModelProperty(value="岗位id，与t_position岗位表主键相同")
    private String positionId;

    /**
     * 简历id
     */
    @TableField(value = "resume_id")
    @ApiModelProperty(value="简历id")
    private String resumeId;

    /**
     * 索引顺序，从1开始
     */
    @TableField(value = "message_index")
    @ApiModelProperty(value="索引顺序，从1开始")
    private Integer messageIndex;

    /**
     * 用户id，与t_user表主键相同
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户id，与t_user表主键相同")
    private String userId;

    /**
     * 用户身份类型：1-求职者、2-HR、3-内推人
     */
    @TableField(value = "user_type")
    @ApiModelProperty(value="用户身份类型：1-求职者、2-HR、3-内推人")
    private String userType;

    /**
     * 消息内容，不超过999字
     */
    @TableField(value = "message")
    @ApiModelProperty(value="消息内容，不超过999字")
    private String message;

    /**
     * 创建时间（UTC时间，包含时分秒毫秒）
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间（UTC时间，包含时分秒毫秒）")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}