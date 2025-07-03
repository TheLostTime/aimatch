package com.example.resp;


import com.example.entity.TCompany;
import com.example.entity.THr;
import com.example.entity.TPosition;
import com.example.entity.TUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="GetSaveJobListResp")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetSaveJobListResp {

    private TPosition tPosition;

    private TUser tUser;

    private TCompany tCompany;

    private THr tHr;

}
