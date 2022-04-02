package com.hsjnb.yd_ecommerce_platform_api.controller;

import com.hsjnb.yd_ecommerce_platform_api.annotation.AnonymousAccess;
import com.hsjnb.yd_ecommerce_platform_api.common.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.entity.AppUser;
import com.hsjnb.yd_ecommerce_platform_api.service.app.AppUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
 * @date : Created in 2022/04/01 22:09
 * @description :
 */

@Api(tags = "App - 登录接口")
@RestController
@RequestMapping(value = "app")
public class AppLoginController {

    private final AppUserService appUserService;

    @Autowired
    public AppLoginController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    /**
     * 用户注册
     * @param appUser appUser
     * @return Result
     */
    @AnonymousAccess
    @PostMapping(value = "register",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "客户注册",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result register(@RequestBody AppUser appUser) {
        return appUserService.register(appUser);
    }

}
