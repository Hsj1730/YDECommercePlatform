package com.hsjnb.yd_ecommerce_platform_api.controller.app;

import com.hsjnb.yd_ecommerce_platform_api.annotation.AnonymousAccess;
import com.hsjnb.yd_ecommerce_platform_api.common.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsOrderAddDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsOrderEvaluateDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsOrderSearchDto;
import com.hsjnb.yd_ecommerce_platform_api.service.app.ShopGoodsOrderService;
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
 * @date : Created in 2022/05/15 12:12
 * @description :
 */

@Api(tags = "App - 订单模块")
@RestController
@RequestMapping(value = "app/goodsOrder")
public class ShopGoodsOrderController {

    private final ShopGoodsOrderService goodsOrderService;

    @Autowired
    public ShopGoodsOrderController(ShopGoodsOrderService goodsOrderService) {
        this.goodsOrderService = goodsOrderService;
    }

    @PostMapping(value = "addGoodsOrder", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "添加商品订单", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result addGoodsOrder(@RequestBody GoodsOrderAddDto goodsOrderAddDto) {
        return goodsOrderService.addGoodsOrder(goodsOrderAddDto);
    }

    @PostMapping(value = "getGoodsOrderList", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "获取商品订单列表", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getGoodsOrderList(@RequestBody GoodsOrderSearchDto dto) {
        return goodsOrderService.getGoodsOrderList(dto);
    }

    @PostMapping(value = "updateStatus/{id}/{status}", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "修改订单状态", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result updateStatus(@PathVariable Integer id,@PathVariable String status) {
        return goodsOrderService.updateStatus(id,status);
    }

    @PostMapping(value = "evaluate", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "评价", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result evaluate(@RequestBody GoodsOrderEvaluateDto dto) {
        return goodsOrderService.evaluate(dto);
    }

    @AnonymousAccess
    @PostMapping(value = "getGoodsOrderDetail/{id}", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "获取订单详情", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getGoodsOrderDetail(@PathVariable Integer id) {
        return goodsOrderService.getGoodsOrderDetail(id);
    }

}
