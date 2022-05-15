package com.hsjnb.yd_ecommerce_platform_api.controller.app;

import com.hsjnb.yd_ecommerce_platform_api.annotation.AnonymousAccess;
import com.hsjnb.yd_ecommerce_platform_api.common.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.service.app.ShopGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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
 * @date : Created in 2022/05/09 23:32
 * @description :
 */

@Api(tags = "App - 商品模块")
@RestController
@RequestMapping(value = "app/goods")
public class ShopGoodsController {

    private final ShopGoodsService shopGoodsService;

    @Autowired
    public ShopGoodsController(ShopGoodsService shopGoodsService) {
        this.shopGoodsService = shopGoodsService;
    }

    /**
     * 查询商品分类列表
     * @return Result
     */
    @AnonymousAccess
    @PostMapping(value = "getGoodsCategoryList", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "获取商品分类列表", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getGoodsCategoryList() {
        return Result.success(shopGoodsService.getGoodsCategoryList());
    }

    /**
     * 获取分类商品列表
     * @return Result
     */
    @AnonymousAccess
    @PostMapping(value = "getGoodsListByCategory/{id}", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "获取分类商品列表", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getGoodsListByCategory(@PathVariable Integer id) {
        return Result.success(shopGoodsService.getGoodsListByCategory(id));
    }

    /**
     * 获取商品列表
     * @return Result
     */
    @AnonymousAccess
    @PostMapping(value = "getGoodsList", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "获取商品列表", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getGoodsList(String search) {
        return Result.success(shopGoodsService.getGoodsList(search));
    }

    @AnonymousAccess
    @PostMapping(value = "getGoodsListTip", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "搜索提示", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getGoodsListTip(String search) {
        return Result.success(shopGoodsService.getGoodsListTip(search));
    }

    /**
     * 获取商品详情
     * @return Result
     */
    @AnonymousAccess
    @PostMapping(value = "getGoodsDetail/{id}", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "获取商品详情", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getGoodsList(@PathVariable Integer id) {
        return Result.success(shopGoodsService.getGoodsDetail(id));
    }

}
