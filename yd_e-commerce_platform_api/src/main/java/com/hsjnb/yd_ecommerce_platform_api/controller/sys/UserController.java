package com.hsjnb.yd_ecommerce_platform_api.controller.sys;

import com.hsjnb.yd_ecommerce_platform_api.common.lang.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.lang.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.AssignRoleDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.UserDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.UserEmailDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.UserPassDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.User;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.UserService;
import com.hsjnb.yd_ecommerce_platform_api.utils.PropertyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 重置密码
     * @param dto dto
     * @return Result
     */
    @PostMapping(value = "resetPassword",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "重置密码",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result resetPassword(@RequestBody UserDto dto) {
        userService.resetPassword(dto);
        return Result.success(200,"密码已重置为：" + PropertyUtil.getProperty(Constant.DEFAULT_PASS),null);
    }

    /**
     * 修改用户是否禁用
     * @param dto dto
     * @return Result
     */
    @PostMapping(value = "changeEnable",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "修改用户是否禁用",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result changeEnable(@RequestBody UserDto dto) {
        return userService.changeEnable(dto) == 1 ? Result.success(null) : Result.fail("操作失败");
    }

    /**
     * 添加用户
     * @param dto dto
     * @return Result
     */
    @PostMapping(value = "addUser",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "添加用户",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result addUser(@RequestBody UserDto dto) {
        userService.addUser(dto);
        return Result.success(200,"添加成功",null);
    }

    /**
     * 查询角色列表和已分配的角色
     * @param user user
     * @return Result
     */
    @PostMapping(value = "queryUserRole",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "查询角色列表和已分配的角色",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result queryUserRole(@RequestBody User user) {
        return Result.success(userService.queryRoleList(user));
    }

    /**
     * 给用户分配角色
     * @param dto dto
     * @return Result
     */
    @PostMapping(value = "assignRoles",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "给用户分配角色",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result assignRoles(@RequestBody AssignRoleDto dto) {
        return Result.success(userService.assignRoles(dto));
    }

    /**
     * 查询登录用户得菜单信息
     * @param authentication authentication
     * @return Result
     */
    @PostMapping(value = "getMenuList",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "查询登录用户得菜单信息",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result getMenuList(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return Result.success(userService.getMenuList(user.getId()));
    }

    /**
     * 检查用户名是否存在
     * @param username username
     * @return Result
     */
    @PostMapping(value = "checkUsername",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "检查用户名是否存在",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result checkUsername(String username) {
        return Result.success(userService.checkUsername(username));
    }

    /**
     * 获取用户详细信息
     * @param authentication authentication
     * @return Result
     */
    @PostMapping(value = "getUserDetailedInfo",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "获取用户详细信息",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result getUserDetailedInfo(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return Result.success(userService.getUserDetailedInfo(user.getId()));
    }

    /**
     * 保存用户详细信息
     * @param user user
     * @return Result
     */
    @PostMapping(value = "saveUserInfo",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "保存用户详细信息",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result saveUserInfo(@RequestBody User user) {
        userService.saveUserInfo(user);
        return Result.success(null);
    }

    /**
     * 验证手机号是否已存在
     * @param mobile mobile
     * @return Result
     */
    @PostMapping(value = "checkMobile",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "验证手机号是否已存在",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result checkMobile(String mobile) {
        return Result.success(userService.checkMobile(mobile));
    }

    /**
     * 头像上传
     * @param file file
     * @param authentication authentication
     * @return Result
     */
    @PostMapping(value = "uploadAvatar")
    @ApiOperation(value = "头像上传",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result uploadAvatar(MultipartFile file,Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        userService.uploadAvatar(file,user);
        return Result.success(null);
    }

    /**
     * 修改密码
     * @param userPassDto userPassVo
     * @return Result
     */
    @PostMapping(value = "updatePass")
    @ApiOperation(value = "修改密码",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result updatePass(@RequestBody UserPassDto userPassDto) {
        return userService.updatePass(userPassDto);
    }

    /**
     * 修改邮箱
     * @return Result
     */
    @PostMapping(value = "resetEmail")
    @ApiOperation(value = "修改邮箱",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result resetEmail(@RequestBody UserEmailDto user) {
        return userService.resetEmail(user);
    }
}
