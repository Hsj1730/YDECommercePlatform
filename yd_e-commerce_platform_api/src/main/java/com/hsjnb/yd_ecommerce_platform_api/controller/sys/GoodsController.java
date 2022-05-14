package com.hsjnb.yd_ecommerce_platform_api.controller.sys;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.hsjnb.yd_ecommerce_platform_api.common.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsDto;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 删除商品信息
     * @param id id
     * @return Result
     */
    @PostMapping(value = "deleteGoods/{id}",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "删除商品信息",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result deleteGoods(@PathVariable Integer id) {
        return goodsService.deleteGoods(id);
    }

    /**
     * 获取商品属性
     * @param id id
     * @return Result
     */
    @PostMapping(value = "getAttrs/{id}",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "获取商品属性",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result getAttrs(@PathVariable Integer id) {
        String str = goodsService.getGoodsAttrResult(id);
        if (StrUtil.isEmpty(str)) {
            return Result.success(null);
        } else {
            return Result.success(JSON.parseObject(str));
        }
    }

    /**
     * 生成商品属性
     * @param id id
     * @param jsonStr jsonStr
     * @return Result
     */
    @PostMapping(value = "isFormatAttr/{id}",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "生成商品属性",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result isFormatAttr(@PathVariable Integer id,@RequestBody String jsonStr) {
        return goodsService.isFormatAttr(id,jsonStr);
    }

    /**
     * 商品属性图片上传
     * @param file file
     * @return Result
     */
    @PostMapping(value = "uploadAttrPic",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "商品属性图片上传",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result isFormatAttr(MultipartFile file) {
        return goodsService.uploadAttrPic(file);
    }

    /**
     * 设置保存商品属性
     * @param id id
     * @param jsonStr jsonStr
     * @return Result
     */
    @PostMapping(value = "setAttrs/{id}",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "设置保存商品属性",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result setAttr(@PathVariable Integer id,@RequestBody String jsonStr) {
        return goodsService.setAttr(id,jsonStr);
    }

    /**
     * 清除属性
     * @param id id
     * @return Result
     */
    @PostMapping(value = "clearAttr/{id}",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "清除属性",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result clearAttr(@PathVariable Integer id) {
        return goodsService.clearGoodsAttr(id,true);
    }

}
