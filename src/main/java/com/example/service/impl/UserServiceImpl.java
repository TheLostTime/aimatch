package com.example.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public Optional<User> findByUsername(String username) {
        logger.info("查找用户: {}", username);
        return userMapper.findByUsername(username);
    }

    @Override
    @Transactional
    public void registerUser(User user) {

        if (usernameExists(user.getUsername())) {
            logger.error("用户名已存在: {}", user.getUsername());
            throw new IllegalArgumentException("用户名已存在");
        }
        if (emailExists(user.getEmail())) {
            logger.error("邮箱已存在: {}", user.getEmail());
            throw new IllegalArgumentException("邮箱已存在");
        }
        
        user.setPassword(SaSecureUtil.sha256(user.getPassword()));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setEnabled(true);
        
        // 设置默认用户类型
        if (user.getUserType() == null) {
            user.setUserType("USER");
        }
        
        int result = userMapper.insert(user);
        if (result != 1) {
            logger.error("用户注册失败: {}", user.getUsername());
            throw new RuntimeException("用户注册失败");
        }
        
        logger.info("用户注册成功: {}", user.getUsername());
    }

    @Override
    public boolean usernameExists(String username) {
        return userMapper.existsByUsername(username) > 0;
    }

    @Override
    public boolean emailExists(String email) {
        return userMapper.existsByEmail(email) > 0;
    }

    @Override
    public void updateLastLoginTime(Long id) {
        logger.info("更新用户登录时间: {}", id);
        userMapper.updateLastLoginTime(id, LocalDateTime.now());
    }

    @Override
    public String login(User user) {
        User dbUser = findByUsername(user.getUsername())
                .orElseThrow(() -> {
                    logger.error("用户名不存在: {}", user.getUsername());
                    return new IllegalArgumentException("用户名或密码错误");
                });

        // 验证密码是否匹配
        if (!SaSecureUtil.sha256(user.getPassword()).equals(dbUser.getPassword())) {
            logger.error("密码错误: {}", user.getUsername());
            throw new IllegalArgumentException("用户名或密码错误");
        }

        // 执行登录操作并获取token
        StpUtil.login(dbUser.getId());
        String token = StpUtil.getTokenValue();
        StpUtil.getSession().set("userInfo", dbUser);

        // 更新用户最后登录时间
        updateLastLoginTime(dbUser.getId());
        logger.info("用户登录成功: {}", user.getUsername());

        return token;
    }

}    