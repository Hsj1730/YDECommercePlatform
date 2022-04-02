package com.hsjnb.yd_ecommerce_platform_api.controller.sys;

import com.hsjnb.yd_ecommerce_platform_api.common.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.MenuDto;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.MenuService;
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
 * @date : Created in 2022/03/21 21:54
 * @description :
 */

@Api(tags = "系统管理 - 菜单接口")
@RestController
@RequestMapping(value = "menu")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 查询菜单列表
     *
     * @return Result
     */
    @PostMapping(value = "getMenuList", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "查询菜单列表", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getMenuList() {
        return Result.success(menuService.getMenuList());
    }

    /**
     * 根据id查询菜单信息
     *
     * @param id id
     * @return Result
     */
    @PostMapping(value = "getMenuById", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "根据id查询菜单信息", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getMenuById(Integer id) {
        return Result.success(menuService.getMenuById(id));
    }

    /**
     * 启用/禁用菜单
     * @param dto MenuDto
     * @return Result
     */
    @PostMapping(value = "changeEnable", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "启用/禁用菜单", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result changeEnable(@RequestBody MenuDto dto) {
        return Result.success(menuService.changeEnable(dto));
    }

    /**
     * 显示/隐藏菜单
     * @param dto MenuDto
     * @return Result
     */
    @PostMapping(value = "changeHidden", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "显示/隐藏菜单", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result changeHidden(@RequestBody MenuDto dto) {
        return Result.success(menuService.changeHidden(dto));
    }

    /**
     * 删除菜单
     * @param id id
     * @return Result
     */
    @PostMapping(value = "deleteMenu",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "删除菜单", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result deleteMenu(Integer id) {
        return menuService.deleteMenu(id) ? Result.success(null) : Result.fail("请先删除该菜单的子菜单");
    }

    /**
     * 添加菜单
     * @param dto MenuDto
     * @return Result
     */
    @PostMapping(value = "addMenu", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "添加菜单", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result addMenu(@RequestBody MenuDto dto) {
        if (menuService.addMenu(dto) == 1) {
            return Result.success("添加成功");
        } else {
            return Result.fail("添加失败");
        }
    }

    /**
     * 编辑菜单
     * @param dto MenuDto
     * @return Result
     */
    @PostMapping(value = "editMenu", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "编辑菜单", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result editMenu(@RequestBody MenuDto dto) {
        if (menuService.editMenu(dto) == 1) {
            return Result.success("修改成功");
        } else {
            return Result.fail("修改失败");
        }
    }

}
