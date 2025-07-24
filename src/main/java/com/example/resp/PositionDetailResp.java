package com.example.resp;


import com.example.entity.TPosition;
import com.example.entity.TPositionKeyWord;
import com.example.entity.TPositionToolbox;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(value="PositionDetailResp")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PositionDetailResp {

    @ApiModelProperty(value="岗位")
    private TPosition tPosition;

    @ApiModelProperty(value="工具箱")
    private TPositionToolbox tPositionToolbox;

    @ApiModelProperty(value="关键词")
    private List<TPositionKeyWord> tPositionKeyWord;

}
