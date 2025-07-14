package com.example.schedule;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.example.entity.THrPaidPermisions;
import com.example.entity.THrPaidPermisionsUseDetail;
import com.example.entity.TPosition;
import com.example.mapper.TPositionMapper;
import com.example.service.TCompanyService;
import com.example.service.THrPaidPermisionsService;
import com.example.service.THrPaidPermisionsUseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Timer {

    @Autowired
    private TCompanyService tCompanyService;
    
    @Autowired
    private TPositionMapper tPositionMapper;

    @Autowired
    private THrPaidPermisionsUseDetailService tHrPaidPermisionsUseDetailService;

    @Autowired
    private THrPaidPermisionsService tHrPaidPermisionsService;

    public final static int offlineDay = 30;

    /**
     * 自动下线岗位
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void autoOfflinePosition() {
        // 查询发布30天的岗位
        List<TPosition> positionList = tPositionMapper.queryOfflinePosition(offlineDay);
        positionList.forEach(tPosition -> {
            tCompanyService.offlinePosition(tPosition.getPositionId());
        });
    }

    /**
     * 更新vip使用权限
     */
    @Scheduled(cron = "0 1 0 * * ?")
    public void updatePermissionDetail() {
        List<THrPaidPermisionsUseDetail> details = tHrPaidPermisionsUseDetailService.list();
        details.forEach(detail -> {
            // 查询用户使用权限
            THrPaidPermisions tHrPaidPermisions = tHrPaidPermisionsService.getById(detail.getUserId());
            detail.setUsedPositionNum(tHrPaidPermisions.getPositionNum());
            detail.setUsedViewResume(tHrPaidPermisions.getViewResume());
            detail.setUsedSayHello(tHrPaidPermisions.getSayHello());
            // 判断更新时间和当前时间是否同一个月
            if (DateUtil.isSameMonth(detail.getCreateTime(), DateUtil.date())) {
                detail.setUsedDownloadNum(tHrPaidPermisions.getDownloadNum());
            }
            detail.setUpdateTime(DateUtil.date());
            tHrPaidPermisionsUseDetailService.updateById(detail);
        });
    }
    
}