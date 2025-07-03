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
    * HR处理应聘简历表，用于记录HR对简历的收藏、沟通状态等信息
    */
@ApiModel(value="THrMarkResume")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "t_hr_mark_resume")
public class THrMarkResume implements Serializable {
    /**
     * 岗位id，与t_position岗位表主键相同
     */
    @TableId(value = "position_id", type = IdType.INPUT)
    @ApiModelProperty(value="岗位id，与t_position岗位表主键相同")
    private String positionId;

    /**
     * 简历id
     */
    @TableField(value = "resume_id")
    @ApiModelProperty(value="简历id")
    private String resumeId;

    /**
     * HR的用户id，方便SQL查询
     */
    @TableField(value = "hr_user_id")
    @ApiModelProperty(value="HR的用户id，方便SQL查询")
    private String hrUserId;

    /**
     * 求职者的用户id，方便SQL查询
     */
    @TableField(value = "employee_user_id")
    @ApiModelProperty(value="求职者的用户id，方便SQL查询")
    private String employeeUserId;

    /**
     * 已收藏标志，默认false
     */
    @TableField(value = "save_flag")
    @ApiModelProperty(value="已收藏标志，默认false")
    private Boolean saveFlag;

    /**
     * 简历状态：say_hello-我发起他未回、new_message-新招呼、communicating-沟通中、interview-已约面、not_suit-不合适
     */
    @TableField(value = "resume_status")
    @ApiModelProperty(value="简历状态：say_hello-我发起他未回、new_message-新招呼、communicating-沟通中、interview-已约面、not_suit-不合适")
    private String resumeStatus;

    /**
     * 岗位开启AI笔试官后的笔试分数
     */
    @TableField(value = "test_scores")
    @ApiModelProperty(value="岗位开启AI笔试官后的笔试分数")
    private String testScores;

    /**
     * 创建时间（UTC时间，包含时分秒毫秒）
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间（UTC时间，包含时分秒毫秒）")
    private Date createTime;

    /**
     * 更新时间（UTC时间，包含时分秒毫秒，首次插入与创建时间相同）
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="更新时间（UTC时间，包含时分秒毫秒，首次插入与创建时间相同）")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}