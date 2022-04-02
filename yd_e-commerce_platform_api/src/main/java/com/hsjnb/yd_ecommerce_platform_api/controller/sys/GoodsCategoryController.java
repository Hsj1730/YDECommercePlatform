package com.hsjnb.yd_ecommerce_platform_api.controller.sys;

import com.hsjnb.yd_ecommerce_platform_api.common.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsCategoryDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsCategory;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.GoodsCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
 * @date : Created in 2022/03/28 11:26
 * @description :
 */

@Api(tags = "系统管理 - 商品分类接口")
@RestController
@RequestMapping(value = "goodsCategory")
public class GoodsCategoryController {

    private final GoodsCategoryService goodsCategoryService;

    @Autowired
    public GoodsCategoryController(GoodsCategoryService goodsCategoryService) {
        this.goodsCategoryService = goodsCategoryService;
    }

    /**
     * 查询商品分类列表
     * @return Result
     */
    @PostMapping(value = "getGoodsCategoryList", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "查询商品分类列表", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getGoodsCategoryList() {
        return Result.success(goodsCategoryService.getGoodsCategoryList());
    }

    /**
     * 添加商品分类
     * @return Result
     */
    @PostMapping(value = "addGoodsCategory", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "添加商品分类", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result addGoodsCategory(@RequestBody GoodsCategory goodsCategory) {
        return goodsCategoryService.addGoodsCategory(goodsCategory) ? Result.success(null) : Result.fail("分类最多能添加2级哦");
    }

    /**
     * 编辑商品分类
     * @return Result
     */
    @PostMapping(value = "editGoodsCategory", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "编辑商品分类", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result editGoodsCategory(@RequestBody GoodsCategoryDto dto) {
        return goodsCategoryService.editGoodsCategory(dto) ? Result.success(null) : Result.fail("分类最多能添加2级哦");
    }

    /**
     * 获取商品分类信息
     * @return Result
     */
    @PostMapping(value = "getGoodsCategoryById", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "获取商品分类信息", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getGoodsCategoryById(Integer id) {
        return goodsCategoryService.getGoodsCategoryById(id);
    }

    /**
     * 删除商品分类信息
     * @return Result
     */
    @PostMapping(value = "deleteGoodsCategory", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "删除商品分类信息", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result deleteGoodsCategory(Integer id) {
        return goodsCategoryService.deleteGoodsCategory(id);
    }

}
