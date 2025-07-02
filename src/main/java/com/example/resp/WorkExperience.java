package com.example.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="WorkExperience")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkExperience {

    @ApiModelProperty(value="顺序")
    private Integer index;

    @ApiModelProperty(value="职位名称")
    private String positionName;

    @ApiModelProperty(value="公司名称")
    private String companyName;

    @ApiModelProperty(value="工作开始时间")
    private String beginDate;

    @ApiModelProperty(value="工作结束时间")
    private String endDate;

}
