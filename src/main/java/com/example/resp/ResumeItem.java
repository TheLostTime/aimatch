package com.example.resp;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(value="ResumeItem")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResumeItem {

    private ResumeMark resumeMark;
    private BaseInfo baseInfo;
    private List<WorkExperience> work;
    private List<Education> education;
    private List<InterestJob> interestJobList;



}
