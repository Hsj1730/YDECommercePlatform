package com.hsjnb.yd_ecommerce_platform_api.service.sys.impl;

import com.hsjnb.yd_ecommerce_platform_api.common.lang.Constant;
import com.hsjnb.yd_ecommerce_platform_api.entity.User;
import com.hsjnb.yd_ecommerce_platform_api.mapper.UserMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.UserService;
import com.hsjnb.yd_ecommerce_platform_api.utils.QiNiuYunUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * @date : Created in 2022/03/20 22:49
 * @description :
 */

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final QiNiuYunUtil qiNiuYunUtil;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, QiNiuYunUtil qiNiuYunUtil) {
        this.userMapper = userMapper;
        this.qiNiuYunUtil = qiNiuYunUtil;
    }

    @Override
    public User getLoginUserInfo(Integer id) {
        User userInfo = userMapper.getLoginUserInfo(id);
        if (StringUtils.equals(userInfo.getIFrame(), Constant.I_FRAME_1)) {
            String avatar = qiNiuYunUtil.getDownloadUrl(userInfo.getAvatar());
            userInfo.setAvatar(avatar);
        }
        return userInfo;
    }
}
