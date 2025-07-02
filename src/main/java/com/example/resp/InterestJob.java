package com.example.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="InterestJob")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InterestJob {

    @ApiModelProperty(value="顺序")
    private Integer index;

    @ApiModelProperty(value="职位类别")
    private String job_category;

    @ApiModelProperty(value="职位名称")
    private String position_name;
}
