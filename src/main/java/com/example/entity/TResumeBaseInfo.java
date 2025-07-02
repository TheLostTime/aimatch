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

/**
    * 求职者简历基础信息表
    */
@ApiModel(value="TResumeBaseInfo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_resume_base_info")
public class TResumeBaseInfo implements Serializable {
    /**
     * 简历id，一人一份简历
     */
    @TableId(value = "resume_id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="简历id，一人一份简历")
    private String resumeId;

    /**
     * 用户id，与t_user表主键相同
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户id，与t_user表主键相同")
    private String userId;

    /**
     * 拥有技能，逗号分隔
     */
    @TableField(value = "skills")
    @ApiModelProperty(value="拥有技能，逗号分隔")
    private String skills;

    /**
     * 资格证书，逗号分隔
     */
    @TableField(value = "certs")
    @ApiModelProperty(value="资格证书，逗号分隔")
    private String certs;

    /**
     * 求职状态，取值：0-离职-随时到岗 1-在职-暂不考虑 2-在职-考虑机会 3-在职-月内到岗
     */
    @TableField(value = "job_search_status")
    @ApiModelProperty(value="求职状态，取值：0-离职-随时到岗 1-在职-暂不考虑 2-在职-考虑机会 3-在职-月内到岗")
    private Integer jobSearchStatus;

    /**
     * 个人优势，自我介绍
     */
    @TableField(value = "personal_strengths")
    @ApiModelProperty(value="个人优势，自我介绍")
    private String personalStrengths;

    private static final long serialVersionUID = 1L;
}