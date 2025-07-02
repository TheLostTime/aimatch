package com.example.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="ResumeMark")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResumeMark {

    @ApiModelProperty(value="已收藏标志boolean")
    private Boolean saveFlag;

    @ApiModelProperty(value="简历状态say_hello:我发起他未回new_message:新招呼communicating：沟通中interview：已约面not_suit：不合适")
    private String resumeStatus;

}
