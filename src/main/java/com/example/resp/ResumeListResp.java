package com.example.resp;

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
    private List<ResumeItem> data;
}
