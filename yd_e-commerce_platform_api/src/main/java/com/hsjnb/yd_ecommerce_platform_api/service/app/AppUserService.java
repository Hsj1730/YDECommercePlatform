package com.hsjnb.yd_ecommerce_platform_api.service.app;

import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.AppUserPassDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.AppUser;
import org.springframework.web.multipart.MultipartFile;

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
 * @date : Created in 2022/03/27 14:33
 * @description :
 */

public interface AppUserService {

    Result register(AppUser user);

    AppUser getLoginUserInfo(Integer userId);

    Result registerValidateUsername(AppUser user);

    Result uploadAvatar(MultipartFile multipartFile, AppUser appUser);

    Result updatePass(AppUserPassDto appUserPassDto);

    Result saveUserInfo(AppUser appUser);

    Result forgotPass(AppUser appUser);

}
