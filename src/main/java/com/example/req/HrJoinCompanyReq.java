package com.example.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@ApiModel(value="HrJoinCompanyReq")
@Data
@AllArgsConstructor
public class HrJoinCompanyReq {

    @ApiModelProperty(value="HR名称")
    private String hrName;

    @ApiModelProperty(value="职务/头衔")
    private String title;

    @ApiModelProperty(value="公司名称")
    private String companyName;

    @ApiModelProperty(value="所属行业")
    private String industry;

    @ApiModelProperty(value="公司规模1:0-20,2:20-99,3:100-499")
    private String scale;

}
