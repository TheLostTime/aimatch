package com.example.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@ApiModel(value="ChatReq")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatReq {

    @ApiModelProperty(value = "消息内容",example = "生成 Java 岗位描述")
    @NotNull(message = "消息内容不能为空")
    private String content;

}
