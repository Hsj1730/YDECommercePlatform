package com.hsjnb.yd_ecommerce_platform_api.controller.sys;

import com.hsjnb.yd_ecommerce_platform_api.common.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.RoleDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.RoleMenuDto;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @date : Created in 2022/03/22 23:05
 * @description :
 */

@Api(tags = "系统管理 - 角色接口")
@RestController
@RequestMapping(value = "role")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * 查询角色列表
     * @param param param
     * @return Result
     */
    @PostMapping(value = "getRoleList", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "查询角色列表", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getRoleList(@RequestBody Map<String,Object> param) {
        return Result.success(roleService.getRoleList(param));
    }

    /**
     * 获取角色已有的菜单
     * @param id id
     * @return Result
     */
    @PostMapping(value = "getMenuTree", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "获取角色已有的菜单", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getMenuTree(Integer id) {
        return Result.success(roleService.getMenuTree(id));
    }

    /**
     * 通过id获取角色
     * @param id id
     * @return Result
     */
    @PostMapping(value = "getRoleById", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "通过id获取角色", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getRoleById(Integer id) {
        return Result.success(roleService.getRoleById(id));
    }

    /**
     * 改变角色禁用状态
     * @param dto dto
     * @return Result
     */
    @PostMapping(value = "changeEnable", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "改变角色禁用状态", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result changeEnable(@RequestBody RoleDto dto) {
        return Result.success(roleService.changeEnable(dto));
    }

    /**
     * 通过id删除角色
     * @param id id
     * @return Result
     */
    @PostMapping(value = "deleteRole", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "通过id删除角色", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result deleteRole(Integer id) {
        return Result.success(roleService.deleteRole(id));
    }

    /**
     * 添加角色
     * @param dto dto
     * @return Result
     */
    @PostMapping(value = "addRole", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "改变角色禁用状态", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result addRole(@RequestBody RoleDto dto) {
        return Result.success(roleService.addRole(dto));
    }

    /**
     * 编辑角色
     * @param dto dto
     * @return Result
     */
    @PostMapping(value = "editRole", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "编辑角色", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result editRole(@RequestBody RoleDto dto) {
        return Result.success(roleService.editRole(dto));
    }

    /**
     * 保存角色菜单
     * @param dto dto
     * @return Result
     */
    @PostMapping(value = "saveRoleMenu", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "保存角色菜单", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result saveRoleMenu(@RequestBody RoleMenuDto dto) {
        return Result.success(roleService.saveRoleMenu(dto));
    }

}
