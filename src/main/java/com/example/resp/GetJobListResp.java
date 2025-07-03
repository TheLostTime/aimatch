package com.example.resp;


import com.example.entity.*;
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
public class GetJobListResp {

    private String positionId;

    private TPosition tPosition;

    private TPositionToolbox tPositionToolbox;

    private THr tHr;

    private TUser tUser;

    private TCompany tCompany;

    private  List<TPositionKeyWord> keyWords;

}
