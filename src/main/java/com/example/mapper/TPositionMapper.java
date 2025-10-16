package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.TPosition;
import com.example.req.GetJobListReq;
import com.example.resp.GetJobListResp;
import com.example.resp.PositionListResp;
import com.example.resp.QueryPositionManageResp;
import com.example.resp.RecommendResumeResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TPositionMapper extends BaseMapper<TPosition> {
    List<TPosition> queryOfflinePosition(int offlineDay);

    List<GetJobListResp> getPositonInfoByReq(GetJobListReq getJobListReq);

    List<PositionListResp> selectPositionList(@Param("positionStatus") String positionStatus,
                                              @Param("userId") String userId);

    List<QueryPositionManageResp> queryPositionManageList(@Param("positionStatus") String positionStatus,
                                                          @Param("currentPage") Integer currentPage,
                                                          @Param("pageSize") Integer pageSize);

    Integer queryPositionManageListSize(@Param("positionStatus") String positionStatus);

    List<RecommendResumeResp> queryRecommendResumeList(@Param("positionId") String positionId,
                                                       @Param("size") Integer size);

    Integer queryPositionOnlineNum(@Param("userId") String userId);

    Integer queryPositionOfflineNum(@Param("userId") String userId);
}