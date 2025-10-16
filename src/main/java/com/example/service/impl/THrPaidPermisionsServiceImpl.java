package com.example.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import org.springframework.util.CollectionUtils;

@Service
public class THrPaidPermisionsServiceImpl extends ServiceImpl<THrPaidPermisionsMapper, THrPaidPermisions> implements THrPaidPermisionsService{

    @Autowired
    private THrPaidPermisionsUseDetailService tHrPaidPermisionsUseDetailService;

    @Override
    public PermissionResp getMyPermission() {
        String userId = StpUtil.getLoginId().toString();
        // 查询我的权限
        List<THrPaidPermisions> tHrPaidPermisions =  this.baseMapper.selectList(new LambdaQueryWrapper<THrPaidPermisions>()
                .eq(THrPaidPermisions::getUserId,userId));
        // 还没买权限，返回全0
        if (CollectionUtils.isEmpty(tHrPaidPermisions)) {
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
        int positionNum = tHrPaidPermisions.stream().mapToInt(THrPaidPermisions::getPositionNum).sum();
        int viewResume = tHrPaidPermisions.stream().mapToInt(THrPaidPermisions::getViewResume).sum();
        int sayHello = tHrPaidPermisions.stream().mapToInt(THrPaidPermisions::getSayHello).sum();
        int downloadNum = tHrPaidPermisions.stream().mapToInt(THrPaidPermisions::getDownloadNum).sum();
        return PermissionResp.builder()
                .positionNum(positionNum)
                .viewResumeNum(viewResume)
                .sayHelloNum(sayHello)
                .downloadNum(downloadNum)
                .remainPositionNum(tHrPaidPermisionsUseDetail.getUsedPositionNum())
                .remainViewResumeNum(tHrPaidPermisionsUseDetail.getUsedViewResume())
                .remainSayHelloNum(tHrPaidPermisionsUseDetail.getUsedSayHello())
                .remainDownloadNum(tHrPaidPermisionsUseDetail.getUsedDownloadNum())
                .build();
    }
}
