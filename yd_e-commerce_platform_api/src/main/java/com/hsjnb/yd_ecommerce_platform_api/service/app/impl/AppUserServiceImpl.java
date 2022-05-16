package com.hsjnb.yd_ecommerce_platform_api.service.app.impl;

import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.AppUserPassDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.AppUser;
import com.hsjnb.yd_ecommerce_platform_api.mapper.app.AppUserMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.app.AppUserService;
import com.hsjnb.yd_ecommerce_platform_api.utils.IdCardUtil;
import com.hsjnb.yd_ecommerce_platform_api.utils.PhoneUtil;
import com.hsjnb.yd_ecommerce_platform_api.utils.QiNiuYunUtil;
import com.hsjnb.yd_ecommerce_platform_api.utils.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        try {
            String password = RSAUtil.decryptByPrivateKey(Base64Utils.decodeFromString(user.getPassword()), privateKey);
            if (user.getUsername().equals("") || user.getUsername() == null) {
                return Result.fail("用户名不能为空");
            }
            if (password.equals("")) {
                return Result.fail("密码不能为空");
            }
            if (user.getPhone().isEmpty()) {
                return Result.fail("手机号码不能为空");
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

    @Override
    public Result registerValidateUsername(AppUser user) {
        AppUser userInfoByAccount = appUserMapper.getUserInfoByAccount(user.getUsername());
        if (userInfoByAccount != null) {
            return Result.fail("用户名已存在");
        }
        return Result.success(null);
    }

    @Override
    public Result uploadAvatar(MultipartFile multipartFile, AppUser appUser) {
        // 获取原先图片，将其删除
        AppUser loginUserInfo = appUserMapper.getLoginUserInfo(appUser.getUserId());
        if (!loginUserInfo.getUserImage().equals(defaultAvatar)) {
            qiNiuYunUtil.delete(loginUserInfo.getUserImage());
        }
        String key = qiNiuYunUtil.upload(multipartFile);
        appUser.setUserImage(key);
        appUserMapper.uploadAvatar(appUser);
        String url = qiNiuYunUtil.getDownloadUrl(key);
        return Result.success(200,"修改成功",url);
    }

    @Override
    public Result updatePass(AppUserPassDto appUserPassDto) {
        try {
            String oldPass = RSAUtil.decryptByPrivateKey(Base64Utils.decodeFromString(appUserPassDto.getOldPass()), privateKey);
            String newPass = RSAUtil.decryptByPrivateKey(Base64Utils.decodeFromString(appUserPassDto.getNewPass()), privateKey);
            String pass = appUserMapper.getPass(appUserPassDto.getUserId());
            if (!new BCryptPasswordEncoder().matches(oldPass,pass)) {
                return Result.fail("密码修改失败，旧密码错误");
            } else if (new BCryptPasswordEncoder().matches(newPass, pass)) {
                return Result.fail("新密码不能与旧密码相同");
            }
            appUserPassDto.setNewPass(new BCryptPasswordEncoder().encode(newPass));
            appUserMapper.updatePass(appUserPassDto);
            return Result.success(200,"密码修改成功",null);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException e) {
            e.printStackTrace();
            return Result.fail("密码修改失败");
        }
    }

    @Override
    public Result saveUserInfo(AppUser appUser) {
        // 判断手机号是否为空
        if (appUser.getPhone() == null || appUser.getPhone().length() == 0) {
            return Result.fail("手机号不能为空");
        }
        // 验证手机号格式
        if (PhoneUtil.isPhoneLegal(appUser.getPhone())) {
            AppUser loginUserInfo = appUserMapper.getLoginUserInfo(appUser.getUserId());
            if (!loginUserInfo.getPhone().equals(appUser.getPhone())) {
                if (appUserMapper.queryUserPhone(appUser.getPhone()) != 0) {
                    return Result.fail("手机号码已存在");
                }
            }
            String regExp = "^[\\u4e00-\\u9fa5]*$";
            Pattern p = Pattern.compile(regExp);
            Matcher m = p.matcher(appUser.getRealName());
            if (!m.matches() || appUser.getRealName() == null || appUser.getRealName().equals("")) {
                return Result.fail("请输入正确的姓名");
            }
            if (!IdCardUtil.isIdCardLegal(appUser.getCardId())) {
                return Result.fail("请输入正确的身份证号");
            }
            appUserMapper.saveUserInfo(appUser);
        } else {
            return Result.fail("请输入正确的手机号码");
        }
        return Result.success(200,"修改成功",null);
    }

    @Override
    public Result forgotPass(AppUser appUser) {
        if (appUser.getUsername().equals("") || appUser.getUsername() == null) {
            return Result.fail("用户名不能为空");
        }
        if (appUser.getPhone() == null || appUser.getPhone().length() == 0) {
            return Result.fail("手机号不能为空");
        }
        if (!PhoneUtil.isPhoneLegal(appUser.getPhone())) {
            return Result.fail("请输入正确的手机号码");
        }
        AppUser userByUserAndPhone = appUserMapper.getUserByUserAndPhone(appUser);
        if (userByUserAndPhone != null) {
            try {
                String password = RSAUtil.decryptByPrivateKey(Base64Utils.decodeFromString(appUser.getPassword()), privateKey);
                appUser.setPassword(new BCryptPasswordEncoder().encode(password));
                appUserMapper.forgotPass(appUser);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException e) {
                e.printStackTrace();
                return Result.fail("忘记密码失败");
            }

        } else {
            return Result.fail("用户名或手机号错误");
        }
        return Result.success(200,"密码修改成功",null);
    }
}
