package com.example.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@ApiModel(value="AuditPositionReq")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditPositionReq {

    @ApiModelProperty(value = "岗位ID")
    @NotNull(message = "positionId不能为空")
    private String positionId;

    @ApiModelProperty(value = "状态2|3，表示通过3拒绝")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @ApiModelProperty(value = "驳回理由")
    private String reason;
}
