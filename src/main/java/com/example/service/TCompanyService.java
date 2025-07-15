package com.example.service;

import com.example.entity.TCompany;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.THrVip;
import com.example.entity.TPosition;
import com.example.entity.TVipPackage;
import com.example.req.GetJobListReq;
import com.example.req.HrActivateReq;
import com.example.req.HrJoinCompanyReq;
import com.example.req.SavePositionReq;
import com.example.resp.GetJobListResp;
import com.example.resp.HrInfoResp;
import com.example.resp.PositionDetailResp;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TCompanyService extends IService<TCompany>{

    void joinCompany(HrJoinCompanyReq hrJoinCompany);

    HrInfoResp getHrStatus();

    void applyCertification(MultipartFile enterpriseLicenseFile, MultipartFile incumbencyCertificateFile);

    void publishPosition(String positionId);

    void activateVip(HrActivateReq hrActivateReq);

    void upgradeVip(HrActivateReq hrActivateReq);

    void savePosition(SavePositionReq savePositionReq);

    void offlinePosition(String positionId);

    List<TCompany> queryCompanyList();

    void auditCompany(String companyId,String status,String reason);

    List<GetJobListResp> getJobList(GetJobListReq getJobListReq);

    PositionDetailResp getPositionDetail(String positionId);

    List<TVipPackage> getVipPackage();
}
