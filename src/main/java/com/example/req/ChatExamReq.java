package com.example.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@ApiModel(value="ChatExamReq")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatExamReq {

    @ApiModelProperty(value = "消息内容")
    private String content;

    @ApiModelProperty(value = "岗位ID")
    @NotNull(message = "positionId不能为空")
    private String positionId;

    @ApiModelProperty(value = "笔试ID,前端定义UUID，确保一场考试中用的是同一个即可,用来保存会话上下文")
    @NotNull(message = "examId不能为空")
    private String examId;

    @ApiModelProperty(value = "开始考试标记,为true时,消息内容可为空,后续答题设置为false")
    @NotNull(message = "startExamFlag不能为空")
    private Boolean startExamFlag;

    @ApiModelProperty(value = "出题数量，默认10道题")
    private int examNum = 10;
}
