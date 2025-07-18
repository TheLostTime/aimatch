package com.example.resp;


import com.example.entity.TCompany;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="HrInfoResp")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HrInfoResp {

    @ApiModelProperty(value="用户ID")
    private String userId;

    @ApiModelProperty(value="头像(二进制文件转字符串存储)")
    private String avatar;

    @ApiModelProperty(value="HR名称")
    private String name;

    @ApiModelProperty(value="HR职务")
    private String title;

    @ApiModelProperty(value="是否加入企业YES、NO")
    private String joinCompanyStatus;

    @ApiModelProperty(value="是否已实名YES、NO")
    private String realNameAuthed;

    @ApiModelProperty(value="是否企业认证YES、NO")
    private String enterpriseCertified;

    @ApiModelProperty(value="公司信息")
    private TCompany company;


    @ApiModelProperty(value="NO-非vip、NORMAL--普通vip、HIGH-豪华vip")
    private String vipType;
}
