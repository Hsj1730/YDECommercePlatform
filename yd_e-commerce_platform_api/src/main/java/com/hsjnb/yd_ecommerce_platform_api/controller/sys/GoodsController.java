package com.hsjnb.yd_ecommerce_platform_api.controller.sys;

import com.hsjnb.yd_ecommerce_platform_api.common.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsDto;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.GoodsService;
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
 * @date : Created in 2022/03/30 21:27
 * @description :
 */

@Api(tags = "系统管理 - 商品接口")
@RestController
@RequestMapping(value = "goods")
public class GoodsController {

    private final GoodsService goodsService;

    @Autowired
    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    /**
     * 查询商品列表
     * @param param param
     * @return Result
     */
    @PostMapping(value = "getGoodsList", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "查询商品列表", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getGoodsList(@RequestBody Map<String,Object> param) {
        return Result.success(goodsService.getGoodsList(param));
    }

    /**
     * 设置商品的上架/下架
     * @param id id
     * @param status status
     * @return Result
     */
    @PostMapping(value = "setGoodsIsShow/{id}", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "设置商品的上架/下架", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result setGoodsIsShow(@PathVariable Integer id,String status) {
        goodsService.setGoodsIsShow(id,status);
        return Result.success(null);
    }

    /**
     * 获取商品信息
     * @param id id
     * @return Result
     */
    @PostMapping(value = "getGoodsInfo/{id}", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "获取商品信息", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getGoodsInfo(@PathVariable Integer id) {
        return Result.success(goodsService.getGoodsInfo(id));
    }

    /**
     * 保存商品信息
     * @param dto dto
     * @return Result
     */
    @PostMapping(value = "saveGoodsInfo", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "保存商品信息", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result saveGoodsInfo(@RequestBody GoodsDto dto) {
        return goodsService.saveGoodsInfo(dto);
    }

}
