package com.hsjnb.yd_ecommerce_platform_api.service.app.impl;

import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.entity.AppUser;
import com.hsjnb.yd_ecommerce_platform_api.mapper.app.AppUserMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.app.AppUserService;
import com.hsjnb.yd_ecommerce_platform_api.utils.PhoneUtil;
import com.hsjnb.yd_ecommerce_platform_api.utils.QiNiuYunUtil;
import com.hsjnb.yd_ecommerce_platform_api.utils.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * █████▒█    ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
 * ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
 * ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 * ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 * ░     ░ ░      ░  ░
 *
 * @author : Hsj1730
 * @version : 1.0
 * @date : Created in 2022/03/27 14:34
 * @description :
 */

@Service
public class AppUserServiceImpl implements AppUserService {

    private final AppUserMapper appUserMapper;

    private final QiNiuYunUtil qiNiuYunUtil;

    @Value("${hsjnb.rsa.privateKey}")
    private String privateKey;

    @Value("${hsjnb.default.defaultAvatar}")
    private String defaultAvatar;

    @Autowired
    public AppUserServiceImpl(AppUserMapper appUserMapper,QiNiuYunUtil qiNiuYunUtil) {
        this.appUserMapper = appUserMapper;
        this.qiNiuYunUtil = qiNiuYunUtil;
    }

    @Override
    public Result register(AppUser user) {
        AppUser userInfoByAccount = appUserMapper.getUserInfoByAccount(user.getUsername());
        if (userInfoByAccount != null) {
            return Result.fail("用户名已存在");
        }
        try {
            String password = RSAUtil.decryptByPrivateKey(Base64Utils.decodeFromString(user.getPassword()), privateKey);
            if (user.getUsername().equals("") || user.getUsername() == null) {
                return Result.fail("用户名不能为空");
            }
            if (password.equals("")) {
                return Result.fail("密码不能为空");
            }
            if (!PhoneUtil.isPhoneLegal(user.getPhone())) {
                return Result.fail("请输入正确的手机号码");
            }
            if (appUserMapper.queryUserPhone(user.getPhone()) != 0) {
                return Result.fail("手机号码已存在");
            }
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            user.setUserImage(defaultAvatar);
            appUserMapper.register(user);
            return Result.success(200,"注册成功",null);
        } catch (NoSuchAlgorithmException | IOException | IllegalBlockSizeException | BadPaddingException | NoSuchPaddingException | InvalidKeySpecException | InvalidKeyException e) {
            e.printStackTrace();
            return Result.fail("程序出现异常");
        }
    }

    @Override
    public AppUser getLoginUserInfo(Integer userId) {
        AppUser loginUserInfo = appUserMapper.getLoginUserInfo(userId);
        loginUserInfo.setPassword("");
        loginUserInfo.setUserImage(qiNiuYunUtil.getDownloadUrl(loginUserInfo.getUserImage()));
        return loginUserInfo;
    }
}
