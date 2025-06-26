package com.example.service.impl;

import com.example.req.HrActivateReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.TVipPackageMapper;
import com.example.entity.TVipPackage;
import com.example.service.TVipPackageService;
@Service
public class TVipPackageServiceImpl extends ServiceImpl<TVipPackageMapper, TVipPackage> implements TVipPackageService{

}
