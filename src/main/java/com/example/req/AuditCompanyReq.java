package com.example.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@ApiModel(value="AuditCompanyReq")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditCompanyReq {

    @ApiModelProperty(value = "公司ID")
    @NotNull(message = "companyId不能为空")
    private String companyId;

    @ApiModelProperty(value = "状态FAIL|PASS")
    @NotNull(message = "状态不能为空")
    private String status;

    @ApiModelProperty(value = "驳回理由")
    private String reason;
}
