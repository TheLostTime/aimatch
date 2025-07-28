package com.example.resp;

import com.example.entity.TEmployee;
import com.example.entity.TEmployeeVip;
import com.example.entity.TResumeInterestJob;
import com.example.entity.TUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(value="EmployeeStatusResp")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeStatusResp {

    private TUser tUser;

    private TEmployee tEmployee;

    private List<TResumeInterestJob> tResumeInterestJob;

    @ApiModelProperty(value="vip类型")
    private String vipType;

    @ApiModelProperty(value="简历ID")
    private String resumeId;
}
