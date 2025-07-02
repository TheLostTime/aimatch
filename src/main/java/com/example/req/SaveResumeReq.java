package com.example.req;

import com.example.entity.*;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(value="SavePositionReq")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveResumeReq {

    private String resumeId;

    private TResumeBaseInfo tResumeBaseInfo;

    private List<TResumeWork> tResumeWork;

    private List<TResumeProject> tResumeProject;

    private List<TResumeEducation> resumeEducation;

    private List<TResumeInterestJob> tResumeInterestJob;
}
