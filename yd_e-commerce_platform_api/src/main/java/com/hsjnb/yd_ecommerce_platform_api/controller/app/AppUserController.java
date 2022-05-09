package com.hsjnb.yd_ecommerce_platform_api.controller.app;

import com.hsjnb.yd_ecommerce_platform_api.annotation.AnonymousAccess;
import com.hsjnb.yd_ecommerce_platform_api.common.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.AppUserPassDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.AppUser;
import com.hsjnb.yd_ecommerce_platform_api.service.app.AppUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
 * @date : Created in 2022/03/27 14:31
 * @description :
 */

@Api(tags = "App - 用户接口")
@RestController
@RequestMapping(value = "app/user")
public class AppUserController {

    private final AppUserService appUserService;

    @Autowired
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    /**
     * 获取登陆用户信息
     * @param authentication authentication
     * @return Result
     */
    @PostMapping(value = "getLoginUserInfo",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "获取登录用户信息",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result getLoginUserInfo(Authentication authentication) {
        AppUser appUser = (AppUser) authentication.getPrincipal();
        AppUser loginUserInfo = appUserService.getLoginUserInfo(appUser.getUserId());
        return Result.success(loginUserInfo);
    }

    /**
     * 头像上传
     * @param file file
     * @param authentication authentication
     * @return Result
     */
    @PostMapping(value = "uploadAvatar")
    @ApiOperation(value = "头像上传",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result uploadAvatar(MultipartFile file, Authentication authentication) {
        AppUser appUser = (AppUser) authentication.getPrincipal();
        return appUserService.uploadAvatar(file,appUser);
    }

    /**
     * 修改密码
     * @param appUserPassDto appUserPassDto
     * @return Result
     */
    @PostMapping(value = "updatePass")
    @ApiOperation(value = "修改密码",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result updatePass(@RequestBody AppUserPassDto appUserPassDto) {
        return appUserService.updatePass(appUserPassDto);
    }

    /**
     * 保存用户详细信息
     * @param appUser appUser
     * @return Result
     */
    @PostMapping(value = "saveUserInfo",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "保存用户详细信息",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result saveUserInfo(@RequestBody AppUser appUser) {
        return appUserService.saveUserInfo(appUser);
    }

    /**
     * 忘记密码
     * @param appUser appUser
     * @return Result
     */
    @AnonymousAccess
    @PostMapping(value = "forgotPass",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "忘记密码",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result forgotPass(@RequestBody AppUser appUser) {
        return appUserService.forgotPass(appUser);
    }

}
