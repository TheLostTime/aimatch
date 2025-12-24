package com.example.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.THr;
import com.example.entity.TUser;
import com.example.exception.BusinessException;
import com.example.mapper.TUserMapper;
import com.example.service.THrService;
import com.example.service.TUserService;
import com.example.util.FileToDbUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.example.constant.Constants.*;

@Service
@Slf4j
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService{
    private static final Logger logger = LoggerFactory.getLogger(TUserServiceImpl.class);

    @Autowired
    private THrService tHrService;

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
    @Transactional
    public void registerUser(TUser user) {
        if (null != findByUsername(user.getAccount())) {
            logger.error("用户名已存在: {}", user.getAccount());
            throw new IllegalArgumentException("用户名已存在");
        }
        // 账号密码应校验长度大小，字母、数字、特殊符号任意两种组合
        String PASSWORD_REGEX =
                "^(?![0-9]+$)(?![a-zA-Z]+$)(?![^0-9a-zA-Z]+$)[0-9A-Za-z\\S]{6,20}$";
        if (!user.getPassword().matches(PASSWORD_REGEX)) {
            logger.error("密码格式错误: {}", user.getPassword());
            throw new BusinessException("密码格式错误");
        }
        user.setPassword(SaSecureUtil.sha256(user.getPassword()));
        int result = baseMapper.insert(user);

        if (user.getUserType().equals(USER_TYPE_HR) || user.getUserType().equals(USER_TYPE_INTRODUCE)) {
            // 插入hr表
            tHrService.save(THr.builder().vipType(VIP_NO).userId(user.getUserId()).realName(2).build());
        }
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
            throw new BusinessException("用户不存在！");
        }

        // 验证密码是否匹配
        if (!SaSecureUtil.sha256(user.getPassword()).equals(dbUser.getPassword())) {
            logger.error("密码错误: {}", user.getAccount());
            throw new BusinessException("用户名或密码错误");
        }

        // 验证用户类型
        if(!user.getUserType().equals(dbUser.getUserType())) {
            throw new BusinessException("请选择正确的入口");
        }

        // 执行登录操作并获取token
        StpUtil.login(dbUser.getUserId(),dbUser.getUserType());
        String token = StpUtil.getTokenValue();
        StpUtil.getSession().set("userInfo", dbUser);

        logger.info("用户登录成功: {}", user.getAccount());
        return token;
    }

    @Override
    public void updateAvatar(MultipartFile avatarFile) {
        String loginId = StpUtil.getLoginId().toString();
        String avatar = FileToDbUtil.fileToStr(avatarFile);
        baseMapper.updateById(TUser.builder()
                .userId(loginId)
                .avatar(avatar)
                .build());
        log.info("更新头像成功");
    }
}
