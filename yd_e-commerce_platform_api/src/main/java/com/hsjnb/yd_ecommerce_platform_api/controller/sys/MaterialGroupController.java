package com.hsjnb.yd_ecommerce_platform_api.controller.sys;

import com.hsjnb.yd_ecommerce_platform_api.common.lang.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.lang.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.MaterialGroupDto;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.MaterialGroupService;
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
 * @date : Created in 2022/03/29 19:28
 * @description :
 */

@Api(tags = "系统管理 - 素材分组接口")
@RestController
@RequestMapping(value = "materialGroup")
public class MaterialGroupController {

    private final MaterialGroupService materialGroupService;

    @Autowired
    public MaterialGroupController(MaterialGroupService materialGroupService) {
        this.materialGroupService = materialGroupService;
    }

    /**
     * 查询素材分组列表
     * @return Result
     */
    @PostMapping(value = "getMaterialGroupList", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "查询素材分组列表", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getMaterialGroupList() {
        return Result.success(materialGroupService.getMaterialGroupList());
    }

    /**
     * 新增素材分组
     * @param groupName groupName
     * @return Result
     */
    @PostMapping(value = "addMaterialGroup", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "新增素材分组", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result addMaterialGroup(String groupName) {
        materialGroupService.addMaterialGroup(groupName);
        return Result.success(null);
    }

    /**
     * 更新素材分组
     * @param dto dto
     * @return Result
     */
    @PostMapping(value = "updateMaterialGroup", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "更新素材分组", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result updateMaterialGroup(@RequestBody MaterialGroupDto dto) {
        materialGroupService.updateMaterialGroup(dto);
        return Result.success(null);
    }

    /**
     * 删除素材分组
     * @param id id
     * @return Result
     */
    @PostMapping(value = "deleteMaterialGroup", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "删除素材分组", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result deleteMaterialGroup(Integer id) {
        materialGroupService.deleteMaterialGroup(id);
        return Result.success(null);
    }

}
