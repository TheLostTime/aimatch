package com.example.service;

import com.example.entity.THrPaidPermisions;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.resp.PermissionResp;
import com.example.resp.RcAndPositionResp;

public interface THrPaidPermisionsService extends IService<THrPaidPermisions>{


    PermissionResp getMyPermission();
}
