package com.hsjnb.yd_ecommerce_platform_api.controller.sys;

import com.hsjnb.yd_ecommerce_platform_api.common.lang.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.lang.Result;
import com.hsjnb.yd_ecommerce_platform_api.entity.User;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
 * @date : Created in 2022/03/20 22:46
 * @description :
 */

@Api(tags = "系统管理 - 用户接口")
@RestController
@RequestMapping(value = "user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获取登陆用户信息
     * @param authentication authentication
     * @return Result
     */
    @PostMapping(value = "getLoginUserInfo",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "获取登录用户信息",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result getLoginUserInfo(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        User loginUserInfo = userService.getLoginUserInfo(user.getId());
        return Result.success(loginUserInfo);
    }

    /**
     * 查询系统用户列表
     * @param param param
     * @return Result
     */
    @PostMapping(value = "getUserList",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "查询系统用户列表",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result getUserList(@RequestBody Map<String,Object> param) {
        return Result.success(userService.getUserList(param));
    }

    /**
     * 删除用户
     * @param id id
     * @return Result
     */
    @PostMapping(value = "deleteUser",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "删除用户",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result deleteUser(Integer id) {
        return Result.success(userService.deleteUser(id));
    }

}
