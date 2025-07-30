package com.example.resp;

import com.example.entity.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(value="TalentListResp")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TalentListResp {

    @ApiModelProperty(value="简历id")
    private String resumeId;

    @ApiModelProperty(value="岗位Id")
    private String positionId;

    private TPosition tPosition;

    private TEmployee tEmployee;

    private THrMarkResume tHrMarkResume;

    private List<TResumeEducation> educations;
}
