package com.example.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.example.entity.THrPaidPermisionsUseDetail;
import com.example.resp.PermissionResp;
import com.example.resp.RcAndPositionResp;
import com.example.service.THrPaidPermisionsUseDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.THrPaidPermisionsMapper;
import com.example.entity.THrPaidPermisions;
import com.example.service.THrPaidPermisionsService;
@Service
public class THrPaidPermisionsServiceImpl extends ServiceImpl<THrPaidPermisionsMapper, THrPaidPermisions> implements THrPaidPermisionsService{

    @Autowired
    private THrPaidPermisionsUseDetailService tHrPaidPermisionsUseDetailService;

    @Override
    public PermissionResp getMyPermission() {
        String userId = StpUtil.getLoginId().toString();
        // 查询我的权限
        THrPaidPermisions tHrPaidPermisions =  this.getById(userId);
        // 还没买权限，返回全0
        if (null == tHrPaidPermisions) {
            return PermissionResp.builder()
                    .positionNum(0)
                    .viewResumeNum(0)
                    .sayHelloNum(0)
                    .downloadNum(0)
                    .remainPositionNum(0)
                    .remainViewResumeNum(0)
                    .remainSayHelloNum(0)
                    .remainDownloadNum(0)
                    .build();
        }
        // 查询我使用情况
        THrPaidPermisionsUseDetail tHrPaidPermisionsUseDetail = tHrPaidPermisionsUseDetailService.getById(userId);
        return PermissionResp.builder()
                .positionNum(tHrPaidPermisions.getPositionNum())
                .viewResumeNum(tHrPaidPermisions.getViewResume())
                .sayHelloNum(tHrPaidPermisions.getSayHello())
                .downloadNum(tHrPaidPermisions.getDownloadNum())
                .remainPositionNum(tHrPaidPermisionsUseDetail.getUsedPositionNum())
                .remainViewResumeNum(tHrPaidPermisionsUseDetail.getUsedViewResume())
                .remainSayHelloNum(tHrPaidPermisionsUseDetail.getUsedSayHello())
                .remainDownloadNum(tHrPaidPermisionsUseDetail.getUsedDownloadNum())
                .build();
    }
}
