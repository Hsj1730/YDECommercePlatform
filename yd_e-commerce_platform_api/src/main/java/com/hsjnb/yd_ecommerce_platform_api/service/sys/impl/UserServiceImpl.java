package com.hsjnb.yd_ecommerce_platform_api.service.sys.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsjnb.yd_ecommerce_platform_api.common.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.AssignRoleDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.UserDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.UserEmailDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.UserPassDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.Menu;
import com.hsjnb.yd_ecommerce_platform_api.entity.Role;
import com.hsjnb.yd_ecommerce_platform_api.entity.User;
import com.hsjnb.yd_ecommerce_platform_api.mapper.sys.RoleMapper;
import com.hsjnb.yd_ecommerce_platform_api.mapper.sys.UserMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.UserService;
import com.hsjnb.yd_ecommerce_platform_api.utils.PropertyUtil;
import com.hsjnb.yd_ecommerce_platform_api.utils.QiNiuYunUtil;
import com.hsjnb.yd_ecommerce_platform_api.utils.RSAUtil;
import com.hsjnb.yd_ecommerce_platform_api.utils.TreeUtil;
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
import java.util.HashMap;
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

    private final RoleMapper roleMapper;

    private final QiNiuYunUtil qiNiuYunUtil;

    @Value("${hsjnb.rsa.privateKey}")
    private String privateKey;

    @Value("${hsjnb.default.defaultAvatar}")
    private String defaultAvatar;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, RoleMapper roleMapper, QiNiuYunUtil qiNiuYunUtil) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.qiNiuYunUtil = qiNiuYunUtil;
    }

    @Override
    public User getLoginUserInfo(Integer id) {
        User userInfo = userMapper.getLoginUserInfo(id);
        userInfo.setAvatar(qiNiuYunUtil.getDownloadUrl(userInfo.getAvatar()));
        return userInfo;
    }

    @Override
    public PageInfo<User> getUserList(Map<String, Object> param) {
        PageHelper.startPage(param);
        List<User> userList = userMapper.getUserList((String) param.get("nickname"));
        // 设置头像和角色名称
        for (User user : userList) {
            user.setAvatar(qiNiuYunUtil.getDownloadUrl(user.getAvatar()));
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

    @Override
    public void resetPassword(UserDto dto) {
        dto.setPassword(new BCryptPasswordEncoder().encode(PropertyUtil.getProperty(Constant.DEFAULT_PASS)));
        userMapper.resetPassword(dto);
    }

    @Override
    public int changeEnable(UserDto dto) {
        return userMapper.changeEnable(dto);
    }

    @Override
    public void addUser(UserDto dto) {
        try {
            String password = RSAUtil.decryptByPrivateKey(Base64Utils.decodeFromString(dto.getPassword()), privateKey);
            if (password.isEmpty()){
                dto.setPassword(new BCryptPasswordEncoder().encode(PropertyUtil.getProperty(Constant.DEFAULT_PASS)));
            }else {
                dto.setPassword(new BCryptPasswordEncoder().encode(password));
            }
            dto.setAvatar(defaultAvatar);
            userMapper.addUser(dto);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> queryRoleList(User user) {
        List<Role> roles = roleMapper.queryRoleList(); // 查询所有的角色列表
        List<Integer> userRoleId = userMapper.getUserRoleId(user.getId()); // 查询选择用户已经拥有的角色
        Map<String,Object> result = new HashMap<>();
        result.put("roles",roles);
        result.put("checked",userRoleId);
        return result;
    }

    @Override
    public int assignRoles(AssignRoleDto dto) {
        // 先删掉之前的
        userMapper.deleteUserRoleByUserId(dto.getUserId());
        if (!dto.getRoleIds().isEmpty()) {
            userMapper.assignRoles(dto);
        }
        return 0;
    }

    @Override
    public List<Menu> getMenuList(Integer userId) {
        List<Menu> menuList = userMapper.getMenuList(userId);
        return TreeUtil.generateTrees(menuList);
    }

    @Override
    public int checkUsername(String username) {
        return userMapper.checkUsername(username);
    }

    @Override
    public User getUserDetailedInfo(Integer id) {
        User user = userMapper.getUserDetailedInfo(id);
        user.setAvatar(qiNiuYunUtil.getDownloadUrl(user.getAvatar()));
        return user;
    }

    @Override
    public void saveUserInfo(User user) {
        userMapper.saveUserInfo(user);
    }

    @Override
    public int checkMobile(String mobile) {
        return userMapper.checkMobile(mobile);
    }

    @Override
    public void uploadAvatar(MultipartFile multipartFile, User user) {
        // 获取原先图片，将其删除
        User userDetailedInfo = userMapper.getUserDetailedInfo(user.getId());
        if (!userDetailedInfo.getAvatar().equals(defaultAvatar)) {
            qiNiuYunUtil.delete(userDetailedInfo.getAvatar());
        }
        String key = qiNiuYunUtil.upload(multipartFile);
        user.setAvatar(key);
        userMapper.uploadAvatar(user);
    }

    @Override
    public Result updatePass(UserPassDto userPassDto) {
        try {
            String oldPass = RSAUtil.decryptByPrivateKey(Base64Utils.decodeFromString(userPassDto.getOldPass()), privateKey);
            String newPass = RSAUtil.decryptByPrivateKey(Base64Utils.decodeFromString(userPassDto.getNewPass()), privateKey);
            String pass = userMapper.getPass(userPassDto.getId());
            if (!new BCryptPasswordEncoder().matches(oldPass,pass)) {
                return Result.fail("密码修改失败，旧密码错误");
            } else if (new BCryptPasswordEncoder().matches(newPass, pass)) {
                return Result.fail("新密码不能与旧密码相同");
            }
            userPassDto.setNewPass(new BCryptPasswordEncoder().encode(newPass));
            userMapper.updatePass(userPassDto);
            return Result.success("密码修改成功");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException e) {
            e.printStackTrace();
            return Result.fail("密码修改失败");
        }
    }

    @Override
    public Result resetEmail(UserEmailDto user) {
        try {
            String password = RSAUtil.decryptByPrivateKey(Base64Utils.decodeFromString(user.getPassword()), privateKey);
            String pass = userMapper.getPass(user.getId());
            if (!new BCryptPasswordEncoder().matches(password,pass)) {
                return Result.fail("邮箱修改失败，密码错误");
            }
            userMapper.resetEmail(user);
            return Result.success("邮箱修改成功");
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException | IOException e) {
            e.printStackTrace();
            return Result.fail("邮箱修改失败");
        }
    }

}
