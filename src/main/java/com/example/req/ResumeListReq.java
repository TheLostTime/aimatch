package com.example.req;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@ApiModel(value="ResumeListReq")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResumeListReq {

    @ApiModelProperty(value="查询类型 default-默认查询、ai-ai智选")
    private String queryType = "default";

    @ApiModelProperty(value="职位id，必输，用于查职位对应行业分类，好与简历求职期望里的行业分类匹配上")
    @NotNull(message = "positionId不能为空")
    private String positionId;

    @ApiModelProperty(value="最低学历")
    private String degreeLowerLimit;

    @ApiModelProperty(example = "1",value="最低工作经验，0-经验不限 1-无经验 2-1年以下 3-1-3年 4-3-5年 5-5-10年 6-10年以上")
    private Integer experienceLowerLimit;

    @ApiModelProperty(example = "1",value="最高工作经验，0-无限制 1-1年以下 2-1-3年 3-3-5年 4-5-10年 5-10年以上")
    private Integer experienceUpperLimit;

    @ApiModelProperty(example = "18",value="年龄下限，18-55表示具体年龄，56-55岁以上")
    private Integer ageLowerLimit;

    @ApiModelProperty(example = "55",value="年龄上限，18-55表示具体年龄，56-55岁以上")
    private Integer ageUpperLimit;

    @ApiModelProperty(value="院校要求")
    private String college;

    @ApiModelProperty(value="专业")
    private String major;

    @ApiModelProperty(value="性别")
    private String gender;

    @ApiModelProperty(value="居住地址")
    private String residentiaAddress;

    @ApiModelProperty(value="hrId无需传")
    private String hrId;











 }
