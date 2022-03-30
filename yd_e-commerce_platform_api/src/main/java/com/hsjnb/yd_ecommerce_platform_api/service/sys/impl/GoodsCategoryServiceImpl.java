package com.hsjnb.yd_ecommerce_platform_api.service.sys.impl;

import com.hsjnb.yd_ecommerce_platform_api.common.lang.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsCategoryDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.MaterialDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsCategory;
import com.hsjnb.yd_ecommerce_platform_api.mapper.sys.GoodsCategoryMapper;
import com.hsjnb.yd_ecommerce_platform_api.mapper.sys.MaterialMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.GoodsCategoryService;
import com.hsjnb.yd_ecommerce_platform_api.utils.QiNiuYunUtil;
import com.hsjnb.yd_ecommerce_platform_api.utils.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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
 * @date : Created in 2022/03/28 11:28
 * @description :
 */

@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

    private final GoodsCategoryMapper goodsCategoryMapper;

    private final MaterialMapper materialMapper;

    private final QiNiuYunUtil qiNiuYunUtil;

    @Autowired
    public GoodsCategoryServiceImpl(GoodsCategoryMapper goodsCategoryMapper, MaterialMapper materialMapper,QiNiuYunUtil qiNiuYunUtil) {
        this.goodsCategoryMapper = goodsCategoryMapper;
        this.materialMapper = materialMapper;
        this.qiNiuYunUtil = qiNiuYunUtil;
    }

    @Override
    public List<GoodsCategory> getGoodsCategoryList() {
        List<GoodsCategory> goodsCategoryList = goodsCategoryMapper.getGoodsCategoryList();
        return TreeUtil.generateTrees(goodsCategoryList);
    }

    @Override
    public boolean addGoodsCategory(GoodsCategory goodsCategory) {
        // 只能达到二级节点
        if (goodsCategory.getParentId() != 0 && goodsCategoryMapper.checkCategory(goodsCategory.getParentId()) != 0) {
            return false;
        } else {
            goodsCategoryMapper.addGoodsCategory(goodsCategory);
            return true;
        }
    }

    @Override
    public Result getGoodsCategoryById(Integer id) {
        // 获取商品分类信息
        GoodsCategoryDto goodsCategory = goodsCategoryMapper.getGoodsCategoryById(id);
        // 获取商品分类得图片资源信息
        MaterialDto material = null;
        if (goodsCategory.getPic() != null && !goodsCategory.getPic().equals("")) {
            material = materialMapper.getMaterialByKey(goodsCategory.getPic());
            material.setRealUrl(qiNiuYunUtil.getDownloadUrl(material.getUrl()));
        }
        Map<String,Object> result = new HashMap<>();
        result.put("goodsCategory",goodsCategory);
        result.put("materialDto",material);
        return Result.success(result);
    }

    @Override
    public boolean editGoodsCategory(GoodsCategoryDto dto) {
        // 只能达到二级节点
        if (dto.getParentId() != 0 && goodsCategoryMapper.checkCategory(dto.getParentId()) != 0) {
            return false;
        } else {
            goodsCategoryMapper.editGoodsCategory(dto);
            return true;
        }
    }

    @Override
    public Result deleteGoodsCategory(Integer id) {
        // 查询该分类下是否有子分类
        if (goodsCategoryMapper.getGoodsCategoryChildren(id) > 0) {
            return Result.fail("请先删除子分类");
        }
        // 查询该分类下是否有商品
        if (goodsCategoryMapper.getGoods(id) > 0) {
            return Result.fail("该分类下存在商品");
        }
        // 删除分类
        goodsCategoryMapper.deleteGoodsCategory(id);
        return Result.success(null);
    }

}
