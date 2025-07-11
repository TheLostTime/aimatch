package com.example.resp;

import com.example.entity.*;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(value="ResumeListResp")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResumeListResp {
    private String resumeId;
    private THrMarkResume tHrMarkResume;
    private TUser tUser;
    private TResumeBaseInfo tResumeBaseInfo;
    private List<TResumeWork> works;
    private List<TResumeEducation> educations;
    private List<TResumeInterestJob> interestJobs;
}
