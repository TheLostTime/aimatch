package com.example.service;

import com.example.entity.TUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface TUserService extends IService<TUser>{
    TUser findByUsername(String username);
    void registerUser(TUser user);
    String login(TUser user);

    void updateAvatar(MultipartFile avatarFile);
}
