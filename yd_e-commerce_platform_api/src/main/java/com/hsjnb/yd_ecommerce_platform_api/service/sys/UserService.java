package com.hsjnb.yd_ecommerce_platform_api.service.sys;

import com.github.pagehelper.PageInfo;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.AssignRoleDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.UserDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.UserEmailDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.UserPassDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.Menu;
import com.hsjnb.yd_ecommerce_platform_api.entity.User;
import org.springframework.web.multipart.MultipartFile;

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

public interface UserService {

    /**
     * 获取登录用户信息
     * @param id 用户id
     * @return user
     */
    User getLoginUserInfo(Integer id);

    PageInfo<User> getUserList(Map<String,Object> param);

    boolean deleteUser(Integer id);

    void resetPassword(UserDto dto);

    int changeEnable(UserDto dto);

    void addUser(UserDto dto);

    Map<String,Object> queryRoleList(User user);

    int assignRoles(AssignRoleDto dto);

    List<Menu> getMenuList(Integer userId);

    int checkUsername(String username);

    User getUserDetailedInfo(Integer id);

    void saveUserInfo(User user);

    int checkMobile(String mobile);

    void uploadAvatar(MultipartFile multipartFile,User user);

    Result updatePass(UserPassDto userPassDto);

    Result resetEmail(UserEmailDto user);

}
