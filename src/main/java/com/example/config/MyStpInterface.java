package com.example.config;

import cn.dev33.satoken.stp.StpInterface;
import com.example.entity.TUser;
import com.example.service.TUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class MyStpInterface implements StpInterface {

    @Autowired
    private TUserService tUserService;

    /**
     * 返回用户的角色列表
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 查询用户信息
        TUser tUser = tUserService.getById(loginId.toString());
        List<String> roleList = new ArrayList<>();
        roleList.add(tUser.getUserType());
        log.info("当前登录用户角色:" + tUser.getUserType());
        return roleList;
    }

    @Override
    public List<String> getPermissionList(Object o, String s) {
        log.info("当前登录用户权限:" + o);
        return null;
    }


}
