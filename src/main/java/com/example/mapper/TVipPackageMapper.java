package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.TVipPackage;
import com.example.req.HrActivateReq;

public interface TVipPackageMapper extends BaseMapper<TVipPackage> {
    TVipPackage queryPackage(HrActivateReq hrActivateReq);
}