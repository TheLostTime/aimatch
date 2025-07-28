package com.example.resp;


import com.example.entity.TPosition;
import com.example.entity.TPositionKeyWord;
import com.example.entity.TPositionToolbox;
import com.example.entity.TUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(value="PositionDetailResp")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PositionDetailResp {

    @ApiModelProperty(value="岗位")
    private TPosition tPosition;

    @ApiModelProperty(value="工具箱")
    private TPositionToolbox tPositionToolbox;

    @ApiModelProperty(value="关键词")
    private List<TPositionKeyWord> tPositionKeyWord;

    @ApiModelProperty(value="头像(二进制文件转字符串存储)")
    private String hrAvatar;

    @ApiModelProperty(value="HR名称")
    private String hrName;

    @ApiModelProperty(value="职务/头衔")
    private String title;

    @ApiModelProperty(value="公司名称")
    private String companyName;

}
