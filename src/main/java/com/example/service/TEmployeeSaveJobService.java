package com.example.service;

import com.example.entity.TEmployeeSaveJob;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.req.SaveJobReq;
import com.example.resp.GetSaveJobListResp;

import java.util.List;

public interface TEmployeeSaveJobService extends IService<TEmployeeSaveJob>{


    void saveJob(SaveJobReq saveJobReq);

    TEmployeeSaveJob getSaveJobStatus(String positionId);

    List<GetSaveJobListResp> getSaveJobList();
}
