package com.example.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@ApiModel(value="QueryPositionManageByPageResp")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryPositionManageByPageResp {

    @ApiModelProperty(value="岗位列表")
    List<QueryPositionManageResp> queryPositionManageRespList;

    @ApiModelProperty(value="岗位列表总数")
    Integer total;
}
