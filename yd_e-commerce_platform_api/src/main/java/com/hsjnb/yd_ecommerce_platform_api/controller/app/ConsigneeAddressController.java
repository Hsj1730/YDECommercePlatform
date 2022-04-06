package com.hsjnb.yd_ecommerce_platform_api.controller.app;

import com.hsjnb.yd_ecommerce_platform_api.common.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.ConsigneeAddressDto;
import com.hsjnb.yd_ecommerce_platform_api.service.app.ConsigneeAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
 * @date : Created in 2022/04/04 16:06
 * @description :
 */

@Api(tags = "App - 收获地址接口")
@RestController
@RequestMapping(value = "app/address")
public class ConsigneeAddressController {

    private final ConsigneeAddressService consigneeAddressService;

    @Autowired
    public ConsigneeAddressController(ConsigneeAddressService consigneeAddressService) {
        this.consigneeAddressService = consigneeAddressService;
    }

    /**
     * 添加收货地址
     * @param dto dto
     * @return Result
     */
    @PostMapping(value = "add",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "添加收货地址",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result add(@RequestBody ConsigneeAddressDto dto) {
        return consigneeAddressService.add(dto);
    }

    /**
     * 获取收货地址列表
     * @param userId userId
     * @return Result
     */
    @PostMapping(value = "getConsigneeAddressList/{userId}",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "获取收货地址列表",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result getConsigneeAddressList(@PathVariable Integer userId) {
        return Result.success(consigneeAddressService.getConsigneeAddressList(userId));
    }

    /**
     * 获取收货地址信息
     * @param id id
     * @return Result
     */
    @PostMapping(value = "getConsigneeAddress/{id}",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "获取收货地址信息",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result getConsigneeAddress(@PathVariable Integer id) {
        return Result.success(consigneeAddressService.getConsigneeAddress(id));
    }

    /**
     * 设置默认收货地址列表
     * @param userId userId
     * @return Result
     */
    @PostMapping(value = "setDefault/{userId}/{id}",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "设置默认收货地址列表",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result setDefault(@PathVariable Integer userId,@PathVariable Integer id) {
        consigneeAddressService.setDefault(userId,id);
        return Result.success(null);
    }

    /**
     * 删除收货地址
     * @param id id
     * @return Result
     */
    @PostMapping(value = "delete/{id}",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "删除收货地址",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result delete(@PathVariable Integer id) {
        consigneeAddressService.delete(id);
        return Result.success(null);
    }

    /**
     * 编辑收货地址
     * @param dto dto
     * @return Result
     */
    @PostMapping(value = "edit",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "编辑收货地址",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result edit(@RequestBody ConsigneeAddressDto dto) {
        return consigneeAddressService.edit(dto);
    }
}
