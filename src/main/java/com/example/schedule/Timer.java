package com.example.schedule;

import cn.hutool.core.date.DateUtil;
import com.example.entity.*;
import com.example.mapper.TPositionMapper;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.constant.Constants.VIP_EXPIRED;

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

    @Autowired
    private THrVipService tHrVipService;

    @Autowired
    private THrService tHrService;

    @Autowired
    private TEmployeeVipService tEmployeeVipService;

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


    /**
     * vip到期
     */
    @Scheduled(cron = "0 17 14 * * ?")
    public void updateVip() {
        // expire_time 到期时间小于当前时间
        List<THrVip> tHrVipList = tHrVipService.lambdaQuery().lt(THrVip::getExpireTime, DateUtil.date()).list();
        tHrVipList.forEach(tHrVip -> {
            tHrVip.setVipType(VIP_EXPIRED);
            tHrVipService.updateById(tHrVip);

            // 更新THr表vip_type字段
            THr tHr = tHrService.getById(tHrVip.getUserId());
            tHr.setVipType(VIP_EXPIRED);
            tHrService.updateById(tHr);

            // 清空使用权限
            THrPaidPermisionsUseDetail tHrPaidPermisionsUseDetail = tHrPaidPermisionsUseDetailService.getById(tHrVip.getUserId());
            tHrPaidPermisionsUseDetail.setUsedPositionNum(0);
            tHrPaidPermisionsUseDetail.setUsedViewResume(0);
            tHrPaidPermisionsUseDetail.setUsedSayHello(0);
            tHrPaidPermisionsUseDetail.setUsedDownloadNum(0);
            tHrPaidPermisionsUseDetailService.updateById(tHrPaidPermisionsUseDetail);
        });

        // 更新求职者vip，expire_time 到期时间小于当前时间
        List<TEmployeeVip> tEmployeeVipList = tEmployeeVipService.lambdaQuery().lt(TEmployeeVip::getExpireTime, DateUtil.date()).list();
        tEmployeeVipList.forEach(tEmployeeVip -> {
            tEmployeeVip.setVipType(VIP_EXPIRED);
            tEmployeeVipService.updateById(tEmployeeVip);
        });
    }
}