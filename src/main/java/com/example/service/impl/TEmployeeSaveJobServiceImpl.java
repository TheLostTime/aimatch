package com.example.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.req.SaveJobReq;
import com.example.resp.GetSaveJobListResp;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.TEmployeeSaveJob;
import com.example.mapper.TEmployeeSaveJobMapper;
import com.example.service.TEmployeeSaveJobService;
@Service
public class TEmployeeSaveJobServiceImpl extends ServiceImpl<TEmployeeSaveJobMapper, TEmployeeSaveJob> implements TEmployeeSaveJobService{

    @Override
    public void saveJob(SaveJobReq saveJobReq) {
        TEmployeeSaveJob tEmployeeSaveJob = TEmployeeSaveJob.builder()
                .positionId(saveJobReq.getPositionId())
                .employeeUserId(StpUtil.getLoginId().toString())
                .saveFlag(saveJobReq.getSaveFlag())
                .build();
        saveOrUpdate(tEmployeeSaveJob);
    }

    @Override
    public TEmployeeSaveJob getSaveJobStatus(String positionId) {
        TEmployeeSaveJob tEmployeeSaveJob = this.getOne(new LambdaQueryWrapper<TEmployeeSaveJob>()
                        .eq(TEmployeeSaveJob::getPositionId, positionId)
                .eq(TEmployeeSaveJob::getEmployeeUserId, StpUtil.getLoginId().toString()));
        return tEmployeeSaveJob;

    }

    @Override
    public List<GetSaveJobListResp> getSaveJobList() {
        List<GetSaveJobListResp> getSaveJobListResps =  this.baseMapper.getSaveJobList(StpUtil.getLoginId().toString());
        return getSaveJobListResps;
    }
}
