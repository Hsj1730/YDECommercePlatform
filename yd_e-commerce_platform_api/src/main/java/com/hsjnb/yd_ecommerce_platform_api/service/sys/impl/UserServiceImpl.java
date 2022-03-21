package com.hsjnb.yd_ecommerce_platform_api.service.sys.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsjnb.yd_ecommerce_platform_api.common.lang.Constant;
import com.hsjnb.yd_ecommerce_platform_api.entity.User;
import com.hsjnb.yd_ecommerce_platform_api.mapper.UserMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.UserService;
import com.hsjnb.yd_ecommerce_platform_api.utils.QiNiuYunUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
            userInfo.setAvatar(qiNiuYunUtil.getDownloadUrl(userInfo.getAvatar()));
        }
        return userInfo;
    }

    @Override
    public PageInfo<User> getUserList(Map<String, Object> param) {
        PageHelper.startPage(param);
        List<User> userList = userMapper.getUserList((String) param.get("nickname"));
        // 设置头像和角色名称
        for (User user : userList) {
            if (StringUtils.equals(user.getIFrame(), Constant.I_FRAME_1)) {
                user.setAvatar(qiNiuYunUtil.getDownloadUrl(user.getAvatar()));
            }
            user.setRoleName(userMapper.getUserRoleName(user.getId()));
        }
        return new PageInfo<>(userList);
    }

    @Override
    public boolean deleteUser(Integer id) {
        userMapper.deleteUser(id);  // 删除用户
        userMapper.deleteUserRoleByUserId(id);  // 删除用户角色
        return true;
    }
}
