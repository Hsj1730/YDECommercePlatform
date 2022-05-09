package com.hsjnb.yd_ecommerce_platform_api.controller.app;

import com.hsjnb.yd_ecommerce_platform_api.annotation.AnonymousAccess;
import com.hsjnb.yd_ecommerce_platform_api.common.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.service.app.ShopRotationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
 * @date : Created in 2022/05/09 22:45
 * @description :
 */

@Api(tags = "App - 轮播图接口")
@RestController
@RequestMapping(value = "app/rotation")
public class ShopRotationController {

    private final ShopRotationService shopRotationService;

    @Autowired
    public ShopRotationController(ShopRotationService shopRotationService) {
        this.shopRotationService = shopRotationService;
    }

    /**
     * 随机获取获取轮播图
     * @return Result
     */
    @AnonymousAccess
    @PostMapping(value = "getRotation",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "随机获取获取轮播图",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result getRotation() {
        return Result.success(shopRotationService.getRotation());
    }
}
