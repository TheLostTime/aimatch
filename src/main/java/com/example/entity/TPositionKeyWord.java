package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@ApiModel(value="com-example-entity-TPositionKeyWord")
@Data
@AllArgsConstructor
@TableName(value = "t_position_key_word")
public class TPositionKeyWord implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="主键id")
    private String id;

    /**
     * 岗位id
     */
    @TableField(value = "position_id")
    @ApiModelProperty(value="岗位id")
    private String positionId;

    /**
     * 关键词
     */
    @TableField(value = "key_word")
    @ApiModelProperty(value="关键词")
    private String keyWord;

    private static final long serialVersionUID = 1L;
}