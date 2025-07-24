package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.TPosition;
import com.example.req.GetJobListReq;
import com.example.resp.GetJobListResp;
import com.example.resp.PositionListResp;
import com.example.resp.QueryPositionManageResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TPositionMapper extends BaseMapper<TPosition> {
    List<TPosition> queryOfflinePosition(int offlineDay);

    List<GetJobListResp> getPositonInfoByReq(GetJobListReq getJobListReq);

    List<PositionListResp> selectPositionList(@Param("positionStatus") String positionStatus,
                                              @Param("userId") String userId);

    List<QueryPositionManageResp> queryPositionManageList(@Param("positionStatus") String positionStatus);
}