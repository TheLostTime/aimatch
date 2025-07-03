package com.example.resp;

import com.example.entity.TResumeInterestJob;
import com.example.entity.TUser;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(value="EmployeeStatusResp")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeStatusResp {

    private TUser tUser;

    private List<TResumeInterestJob> tResumeInterestJob;

}
