package com.example.schedule;

import com.example.entity.TPosition;
import com.example.mapper.TPositionMapper;
import com.example.service.TCompanyService;
import io.swagger.annotations.ApiModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@ApiModel(value = "定时任务")
@Component
public class Timer {

    @Autowired
    private TCompanyService tCompanyService;
    
    @Autowired
    private TPositionMapper tPositionMapper;

    public final static int offlineDay = 15;

    /**
     * 自动下线岗位
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void autoOfflinePosition() {
        // 查询发布15天的岗位
        List<TPosition> positionList = tPositionMapper.queryOfflinePosition(offlineDay);
        positionList.forEach(tPosition -> {
            tCompanyService.offlinePosition(tPosition.getPositionId());
        });
    }
    
}