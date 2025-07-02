package com.example.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="Education")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Education {

    @ApiModelProperty(value="顺序")
    private Integer index;

    @ApiModelProperty(value="学历")
    private String degree;

    @ApiModelProperty(value="学校名称")
    private String college;

    @ApiModelProperty(value="专业")
    private String major;

    @ApiModelProperty(value="在校开始时间")
    private String beginDate;

    @ApiModelProperty(value="在校结束时间")
    private String endDate;
}
