package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.TEmployeeSaveJob;
import com.example.resp.GetSaveJobListResp;

import java.util.List;

public interface TEmployeeSaveJobMapper extends BaseMapper<TEmployeeSaveJob> {
    List<GetSaveJobListResp> getSaveJobList(String userId);
}