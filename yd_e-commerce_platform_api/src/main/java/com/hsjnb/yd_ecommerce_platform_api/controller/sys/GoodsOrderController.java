package com.hsjnb.yd_ecommerce_platform_api.controller.sys;

import com.hsjnb.yd_ecommerce_platform_api.common.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsOrder;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.GoodsOrderService;
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
 * @date : Created in 2022/05/17 1:31
 * @description :
 */

@Api(tags = "系统管理 - 订单接口")
@RestController
@RequestMapping(value = "goodsOrder")
public class GoodsOrderController {

    private final GoodsOrderService goodsOrderService;

    @Autowired
    public GoodsOrderController(GoodsOrderService goodsOrderService) {
        this.goodsOrderService = goodsOrderService;
    }

    @PostMapping(value = "getGoodsOrderList", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "获取订单列表", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getGoodsOrderList(@RequestBody Map<String,Object> param) {
        return Result.success(goodsOrderService.getGoodsOrderList(param));
    }

    @PostMapping(value = "saveRemark", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "修改订单备注", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result saveRemark(@RequestBody GoodsOrder dto) {
        return goodsOrderService.saveRemark(dto);
    }

    @PostMapping(value = "deleteGoodsOrder/{id}", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "删除订单", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result deleteGoodsOrder(@PathVariable Integer id) {
        return goodsOrderService.deleteGoodsOrder(id);
    }

    @PostMapping(value = "getExpressList", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "获取快递公司列表", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getExpressList() {
        return goodsOrderService.getExpressList();
    }

    @PostMapping(value = "saveExpress", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "保存物流信息", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result saveExpress(@RequestBody GoodsOrder dto) {
        return goodsOrderService.saveExpress(dto);
    }

    @PostMapping(value = "receipt/{id}", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "进行收货", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result receipt(@PathVariable Integer id) {
        return goodsOrderService.receipt(id);
    }

    @PostMapping(value = "success/{id}", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "交易完成", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result success(@PathVariable Integer id) {
        return goodsOrderService.success(id);
    }

}
