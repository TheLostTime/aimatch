package com.example.resp;

import com.example.entity.TCompany;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@ApiModel(value="QueryCompanyResp")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryCompanyResp {

    @ApiModelProperty(value="公司列表")
    List<TCompany> tCompanyList;

    @ApiModelProperty(value="公司列表总数")
    Integer total;
}
