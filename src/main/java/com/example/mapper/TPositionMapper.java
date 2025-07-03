package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.TPosition;
import com.example.req.GetJobListReq;
import com.example.resp.GetJobListResp;

import java.util.List;

public interface TPositionMapper extends BaseMapper<TPosition> {
    List<TPosition> queryOfflinePosition(int offlineDay);

    List<GetJobListResp> getPositonInfoByReq(GetJobListReq getJobListReq);
}