package com.example.req;

import com.example.entity.TPosition;
import com.example.entity.TPositionKeyWord;
import com.example.entity.TPositionToolbox;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@ApiModel(value="SavePositionReq")
@Data
@AllArgsConstructor
public class SavePositionReq {

    TPosition position;

    TPositionToolbox toolbox;

    List<TPositionKeyWord> keyWords;
}
