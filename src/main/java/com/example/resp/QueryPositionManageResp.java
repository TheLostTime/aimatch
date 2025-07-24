package com.example.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@ApiModel(value="QueryPositionManageResp")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryPositionManageResp {

    @ApiModelProperty(value="岗位id")
    private String positionId;

    @ApiModelProperty(value="用户id")
    private String userId;

    @ApiModelProperty(value="岗位类型0-HR岗位1-内推岗位")
    private String positionType;

    @ApiModelProperty(value="岗位发布状态0-草稿(下线变草稿)1-审核中2-在线3-驳回")
    private Integer positionStatus;

    @ApiModelProperty(value="岗位名称")
    private String positionName;

    @ApiModelProperty(value="职位类别")
    private String jobCategory;

    @ApiModelProperty(value="更新时间-自动下线依据")
    private Date updateTime;

    @ApiModelProperty(value="公司名称")
    private String companyName;

    @ApiModelProperty(value="发布人")
    private String hrName;

}
