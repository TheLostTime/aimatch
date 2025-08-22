package com.example.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="RecommendResumeResp")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendResumeResp {

    @ApiModelProperty(value="岗位Id")
    private String positionId;

    @ApiModelProperty(value="简历Id")
    private String resumeId;

    @ApiModelProperty(value="姓名")
    private String name;

    @ApiModelProperty(value="性别")
    private String gender;

    @ApiModelProperty(value="出生日期")
    private String birthDate;

    @ApiModelProperty(value="学历")
    private String degree;

    @ApiModelProperty(value="专业")
    private String major;

    @ApiModelProperty(value="ai笔试得分")
    private String testScores;

}
