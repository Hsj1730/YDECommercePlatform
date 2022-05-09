package com.hsjnb.yd_ecommerce_platform_api.controller.sys;

import com.hsjnb.yd_ecommerce_platform_api.common.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.MemberDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.RotationDto;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.RotationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
 * @date : Created in 2022/05/09 21:11
 * @description :
 */

@Api(tags = "系统管理 - 轮播图接口")
@RestController
@RequestMapping(value = "rotation")
public class RotationController {

    private final RotationService rotationService;

    @Autowired
    public RotationController(RotationService rotationService) {
        this.rotationService = rotationService;
    }

    /**
     * 查询商城轮播图列表
     * @param param param
     * @return Result
     */
    @PostMapping(value = "getRotationList", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "查询商城轮播图列表", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getRotationList(@RequestBody Map<String,Object> param) {
        return Result.success(rotationService.getRotationList(param));
    }

    /**
     * 删除商城轮播图
     * @param id id
     * @return Result
     */
    @PostMapping(value = "deleteRotation/{id}", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "删除商城轮播图", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result deleteRotation(@PathVariable Integer id) {
        return rotationService.deleteRotation(id);
    }

    /**
     * 更改商城轮播图状态
     * @param id id
     * @return Result
     */
    @PostMapping(value = "setRotationEnable/{id}/{enable}", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "更改商城轮播图状态", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result setRotationEnable(@PathVariable Integer id,@PathVariable String enable) {
        return rotationService.setRotationEnable(id,enable);
    }

    /**
     * 保存轮播图信息
     * @param dto dto
     * @return Result
     */
    @PostMapping(value = "saveRotationInfo",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "保存轮播图信息", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result saveRotationInfo(@RequestBody RotationDto dto) {
        return rotationService.saveRotationInfo(dto);
    }

    /**
     * 获取轮播图信息
     * @param id id
     * @return Result
     */
    @PostMapping(value = "getRotationInfo/{id}",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "获取轮播图信息", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getRotationInfo(@PathVariable("id") Integer id) {
        return Result.success(rotationService.getRotationInfo(id));
    }
}
