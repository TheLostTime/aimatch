package com.example.resp;

import com.example.entity.*;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(value="ResumeDetailHrResp")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDetailHrResp {

    private Boolean overViewResumeFlag;

    private THrMarkResume hrMarkResume;

    private TUser user;

    private TEmployee employee;

    private TResumeBaseInfo baseInfo;

    private List<TResumeEducation> educations;

    private List<TResumeWork> works;

    private List<TResumeProject> projects;

    private List<TResumeInterestJob> interestJobs;

}
