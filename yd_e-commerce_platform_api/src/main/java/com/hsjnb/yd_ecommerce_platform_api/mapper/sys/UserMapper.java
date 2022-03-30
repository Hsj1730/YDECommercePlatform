package com.hsjnb.yd_ecommerce_platform_api.mapper.sys;

import com.hsjnb.yd_ecommerce_platform_api.dto.AssignRoleDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.UserDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.UserEmailDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.UserPassDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.Menu;
import com.hsjnb.yd_ecommerce_platform_api.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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
 * @date : Created in 2022/03/16 22:19
 * @description :
 */

@Mapper
@Repository
public interface UserMapper {

    User getLoginUserInfo(@Param("id") Integer id);

    User getUserByUsername(@Param("username") String username);

    List<String> getUserRoleCode(@Param("id") Integer id);

    List<User> getUserList(@Param("nickname") String nickname);

    List<String> getUserRoleName(@Param("userId") Integer id);

    void deleteUser(@Param("id") Integer id);

    void deleteUserRoleByUserId(@Param("userId") Integer id);

    int resetPassword(@Param("dto") UserDto dto);

    int changeEnable(@Param("dto") UserDto dto);

    void addUser(@Param("dto") UserDto dto);

    List<Integer> getUserRoleId(@Param("userId") Integer userId);

    int assignRoles(AssignRoleDto dto);

    List<Menu> getMenuList(@Param("userId") Integer userId);

    int checkUsername(@Param("username") String username);

    User getUserDetailedInfo(@Param("id") Integer id);

    void saveUserInfo(@Param("user") User user);

    int checkMobile(@Param("mobile") String mobile);

    void uploadAvatar(@Param("user") User user);

    String getPass(@Param("id") Integer id);

    void updatePass(@Param("user") UserPassDto userPassDto);

    void resetEmail(@Param("user") UserEmailDto user);
}
