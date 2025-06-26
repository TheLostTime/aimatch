package com.example.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.TUser;
import com.example.mapper.TUserMapper;
import com.example.service.TUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService{
    private static final Logger logger = LoggerFactory.getLogger(TUserServiceImpl.class);

    @Override
    public TUser findByUsername(String account) {
        logger.info("查找用户: {}", account);
        return lambdaQuery()
                .eq(TUser::getAccount, account)
                .list()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public void registerUser(TUser user) {
        if (null != findByUsername(user.getAccount())) {
            logger.error("用户名已存在: {}", user.getAccount());
            throw new IllegalArgumentException("用户名已存在");
        }
        user.setPassword(SaSecureUtil.sha256(user.getPassword()));

        int result = baseMapper.insert(user);
        if (result != 1) {
            logger.error("用户注册失败: {}", user.getAccount());
            throw new RuntimeException("用户注册失败");
        }
        logger.info("用户注册成功: {}", user.getAccount());
    }

    @Override
    public String login(TUser user) {
        TUser dbUser = findByUsername(user.getAccount());
        if (null == dbUser) {
            throw new IllegalArgumentException("用户不存在 ");
        }
        // 验证密码是否匹配
        if (!SaSecureUtil.sha256(user.getPassword()).equals(dbUser.getPassword())) {
            logger.error("密码错误: {}", user.getAccount());
            throw new IllegalArgumentException("用户名或密码错误");
        }

        // 执行登录操作并获取token
        StpUtil.login(dbUser.getUserId());
        String token = StpUtil.getTokenValue();
        StpUtil.getSession().set("userInfo", dbUser);

        logger.info("用户登录成功: {}", user.getAccount());
        return token;
    }
}
