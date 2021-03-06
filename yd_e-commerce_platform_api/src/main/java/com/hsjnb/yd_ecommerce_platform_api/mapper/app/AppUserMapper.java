package com.hsjnb.yd_ecommerce_platform_api.mapper.app;

import com.hsjnb.yd_ecommerce_platform_api.dto.AppUserPassDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.UserPassDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.AppUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
 * @date : Created in 2022/03/27 14:50
 * @description :
 */

@Mapper
@Repository
public interface AppUserMapper {

    AppUser getUserInfoByAccount(@Param("username") String username);

    void register(@Param("user") AppUser user);

    Integer queryUserPhone(@Param("phone") String phone);

    AppUser getLoginUserInfo(@Param("userId") Integer userId);

    void uploadAvatar(@Param("user") AppUser user);

    String getPass(@Param("userId") Integer userId);

    void updatePass(@Param("user") AppUserPassDto appUserPassDto);

    void saveUserInfo(@Param("user") AppUser appUser);

    AppUser getUserByUserAndPhone(@Param("user") AppUser appUser);

    void forgotPass(@Param("user") AppUser appUser);

}
