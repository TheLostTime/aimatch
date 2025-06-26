package com.example.service;

import com.example.entity.TUser;
import com.baomidou.mybatisplus.extension.service.IService;
public interface TUserService extends IService<TUser>{
    TUser findByUsername(String username);
    void registerUser(TUser user);
    String login(TUser user);
}
