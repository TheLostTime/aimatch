package com.example.resp;

import com.example.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(value="ChatSessionResp")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatSessionResp {

    @ApiModelProperty(value = "聊天关系")
    private THrMarkResume tHrMarkResume;

    @ApiModelProperty(value = "HrUser信息")
    private TUser tUserHr;

    @ApiModelProperty(value = "EmployeeUser信息")
    private TUser tUserEmployee;

    @ApiModelProperty(value = "最后一次的聊条记录")
    private TImMessage imMessage;

    @ApiModelProperty(value = "公司信息")
    private TCompany tCompany;

    @ApiModelProperty(value = "Hr信息")
    private THr tHr;

    @ApiModelProperty(value = "求职者信息")
    private TEmployee tEmployee;

    @ApiModelProperty(value = "工作信息")
    private TPosition tPosition;


}
