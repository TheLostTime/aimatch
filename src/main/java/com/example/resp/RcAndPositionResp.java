package com.example.resp;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="RcAndPositionResp")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RcAndPositionResp {

    @ApiModelProperty(value="新招呼人才")
    private Integer rcNewNum;

    @ApiModelProperty(value="收藏人才")
    private Integer rcMarkNum;

    @ApiModelProperty(value="在线职位")
    private Integer positionOnlineNum;

    @ApiModelProperty(value="未上线职位")
    private Integer positionOfflineNum;

}
