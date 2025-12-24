package com.example.resp;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="PermissionResp")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionResp {

    @ApiModelProperty(value="竞招职位数量，AI高匹配简历推荐、AI笔试官、企业出海提醒的可用职位数量")
    private Integer positionNum;

    @ApiModelProperty(value="每日主动查看简历数量")
    private Integer viewResumeNum;

    @ApiModelProperty(value="每日主动打招呼次数")
    private Integer sayHelloNum;

    @ApiModelProperty(value="每月可下载简历数量")
    private Integer downloadNum;

    @ApiModelProperty(value="剩余竞招职位数量，AI高匹配简历推荐、AI笔试官、企业出海提醒的可用职位数量")
    private Integer remainPositionNum;

    @ApiModelProperty(value="剩余每日主动查看简历数量")
    private Integer remainViewResumeNum;

    @ApiModelProperty(value="剩余每日主动打招呼次数")
    private Integer remainSayHelloNum;

    @ApiModelProperty(value="剩余每月可下载简历数量")
    private Integer remainDownloadNum;

}
