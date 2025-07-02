package com.example.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="BaseInfo")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseInfo {

    @ApiModelProperty(value="简历id")
    private String resume_id;

    @ApiModelProperty(value="求职者用户id")
    private String user_id;

    @ApiModelProperty(value="头像")
    private String avatar;

    @ApiModelProperty(value="工作经验")
    private String work_experience;

    @ApiModelProperty(value="年龄")
    private Integer age;
}
