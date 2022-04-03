package com.hsjnb.yd_ecommerce_platform_api.controller.sys;

import com.hsjnb.yd_ecommerce_platform_api.common.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.ExpressDto;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.ExpressService;
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
 * @date : Created in 2022/04/03 19:17
 * @description :
 */

@Api(tags = "系统管理 - 物流接口")
@RestController
@RequestMapping(value = "express")
public class ExpressController {

    private final ExpressService expressService;

    @Autowired
    public ExpressController(ExpressService expressService) {
        this.expressService = expressService;
    }

    /**
     * 查询物流快递公司列表
     * @param param param
     * @return Result
     */
    @PostMapping(value = "getExpressList", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "查询物流快递公司列表", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getExpressList(@RequestBody Map<String,Object> param) {
        return Result.success(expressService.getExpressList(param));
    }

    /**
     * 保存物流快递公司信息
     * @param dto dto
     * @return Result
     */
    @PostMapping(value = "saveExpressInfo", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "保存物流快递公司信息", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result saveExpressInfo(@RequestBody ExpressDto dto) {
        return expressService.saveExpressInfo(dto);
    }

    /**
     * 删除物流快递公司
     * @param id id
     * @return Result
     */
    @PostMapping(value = "deleteExpress/{id}", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "删除物流快递公司", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result deleteExpress(@PathVariable Integer id) {
        return expressService.deleteExpress(id);
    }

    /**
     * 验证物流快递公司编号是否存在
     * @param id id
     * @return Result
     */
    @PostMapping(value = "checkCode/{id}/{code}", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "删除物流快递公司", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result checkCode(@PathVariable Integer id,@PathVariable String code) {
        return Result.success(expressService.checkCode(id,code));
    }

    /**
     * 查询物流快递公司信息
     * @param id id
     * @return Result
     */
    @PostMapping(value = "getExpressInfo/{id}", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "查询物流快递公司信息", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getExpressInfo(@PathVariable Integer id) {
        return expressService.getExpressInfo(id);
    }

    /**
     * 更改物流快递公司状态
     * @param id id
     * @return Result
     */
    @PostMapping(value = "setExpressEnable/{id}/{enable}", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "更改物流快递公司状态", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result setExpressEnable(@PathVariable Integer id,@PathVariable String enable) {
        return expressService.setExpressEnable(id,enable);
    }

}
