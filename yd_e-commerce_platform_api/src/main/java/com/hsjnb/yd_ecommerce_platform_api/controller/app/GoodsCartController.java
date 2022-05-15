package com.hsjnb.yd_ecommerce_platform_api.controller.app;

import com.hsjnb.yd_ecommerce_platform_api.annotation.AnonymousAccess;
import com.hsjnb.yd_ecommerce_platform_api.common.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsOrderAddDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsCart;
import com.hsjnb.yd_ecommerce_platform_api.service.app.GoodsCartService;
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
 * @date : Created in 2022/05/15 10:09
 * @description :
 */

@Api(tags = "App - 购物车模块")
@RestController
@RequestMapping(value = "app/goodsCart")
public class GoodsCartController {

    private final GoodsCartService goodsCartService;

    @Autowired
    public GoodsCartController(GoodsCartService goodsCartService) {
        this.goodsCartService = goodsCartService;
    }

    @PostMapping("getGoodsCartList/{id}")
    @ApiOperation(value = "获取购物车列表", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getGoodsCartList(@PathVariable Integer id) {
        return goodsCartService.getGoodsCartList(id);
    }

    @PostMapping("addGoodsCart")
    @ApiOperation(value = "添加商品到购物车列表", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result addGoodsCart(@RequestBody GoodsCart goodsCart) {
        return goodsCartService.addGoodsCart(goodsCart);
    }

    @PostMapping("delGoodsCart")
    @ApiOperation(value = "删除购物车", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result delGoodsCart(String cartStr) {
        return goodsCartService.delGoodsCart(cartStr);
    }

    @PostMapping("updateGoodsCartGoodsNum/{id}/{num}")
    @ApiOperation(value = "修改购物车商品数量", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result updateGoodsCartGoodsNum(@PathVariable Integer id,@PathVariable Integer num) {
        return goodsCartService.updateGoodsCartGoodsNum(id,num);
    }

    @PostMapping("addGoodsOrder")
    @ApiOperation(value = "添加商品订单", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result addGoodsOrder(@RequestBody GoodsOrderAddDto goodsOrderAddDto) {
        return goodsCartService.addGoodsOrder(goodsOrderAddDto);
    }

}
