package com.example.resp;

import com.example.entity.*;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(value="TalentListResp")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TalentListResp {
    private TEmployee tEmployee;
    private THrMarkResume tHrMarkResume;
}
