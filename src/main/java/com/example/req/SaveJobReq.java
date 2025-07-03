package com.example.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="SaveJobReq")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveJobReq {

    @ApiModelProperty(value="职位id，必输")
    private String positionId;

    @ApiModelProperty(value="0-取消收藏 1-收藏")
    private Boolean saveFlag;
}
