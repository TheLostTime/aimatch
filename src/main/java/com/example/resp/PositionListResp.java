package com.example.resp;


import com.example.entity.TPosition;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="PositionListResp")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PositionListResp {

    @ApiModelProperty(value="岗位信息")
    private TPosition tPosition;

    @ApiModelProperty(value="新招呼数量")
    private Integer newResumeNum;
}
