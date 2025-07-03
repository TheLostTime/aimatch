package com.example.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="getJobListReq")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetJobListReq {

    @ApiModelProperty(value = "热词，支持精确搜索", example = "Java")
    private String keyWord;

    @ApiModelProperty(value = "职位名称，支持模糊搜索", example = "开发工程师")
    private String positionName;

    @ApiModelProperty(value = "工作性质", example = "full_time", allowableValues = "full_time,part_time,internship,fresh_campus")
    private String workType;

    @ApiModelProperty(value = "薪水下限（千）", example = "10")
    private Integer salaryLowerLimit;

    @ApiModelProperty(value = "薪水上限（千）", example = "20")
    private Integer salaryUpperLimit;

    @ApiModelProperty(value = "最低经验：经验不限无经验1年以下1-3年3-5年5-10年10年以上(从0-7)")
    private Integer experienceLowerLimit;

    @ApiModelProperty(value = "最低学历：不限初中及以下高中中专/中技大专本科硕士MBA/EMBA博士(从0-9)")
    private Integer degreeLowerLimit;

    @ApiModelProperty(value = "期望行业（对应HR发布岗位时的职位类别）")
    private String jobCategory;

    @ApiModelProperty(value = "公司规模", example = "100-500人")
    private String scale;

    @ApiModelProperty(value = "岗位类型", example = "1", allowableValues = "0,1")
    private String positionType;

}
