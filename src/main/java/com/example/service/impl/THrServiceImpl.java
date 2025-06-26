package com.example.service.impl;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.example.entity.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.THr;
import com.example.mapper.THrMapper;
import com.example.service.THrService;
@Service
public class THrServiceImpl extends ServiceImpl<THrMapper, THr> implements THrService{

    @Autowired
    private THrMapper hrMapper;

    @Override
    public void realName() {
        SaSession saSession = StpUtil.getSession();
        TUser userInfo = (TUser) saSession.get("userInfo");
        // 根据主键更新real_name为1
        hrMapper.updateById(THr.builder().userId(userInfo.getUserId()).realName(1).build());
    }
}
