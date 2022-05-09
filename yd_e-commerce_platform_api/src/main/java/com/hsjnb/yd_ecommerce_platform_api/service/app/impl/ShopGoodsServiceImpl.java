package com.hsjnb.yd_ecommerce_platform_api.service.app.impl;

import com.hsjnb.yd_ecommerce_platform_api.dto.ShopGoodsDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsCategory;
import com.hsjnb.yd_ecommerce_platform_api.mapper.app.ShopGoodsMapper;
import com.hsjnb.yd_ecommerce_platform_api.mapper.sys.MaterialMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.app.ShopGoodsService;
import com.hsjnb.yd_ecommerce_platform_api.utils.QiNiuYunUtil;
import com.hsjnb.yd_ecommerce_platform_api.utils.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
 * @date : Created in 2022/05/09 23:34
 * @description :
 */

@Service
public class ShopGoodsServiceImpl implements ShopGoodsService {

    private final ShopGoodsMapper shopGoodsMapper;

    private final QiNiuYunUtil qiNiuYunUtil;

    private final MaterialMapper materialMapper;

    @Autowired
    public ShopGoodsServiceImpl(ShopGoodsMapper shopGoodsMapper, QiNiuYunUtil qiNiuYunUtil, MaterialMapper materialMapper) {
        this.shopGoodsMapper = shopGoodsMapper;
        this.qiNiuYunUtil = qiNiuYunUtil;
        this.materialMapper = materialMapper;
    }

    @Override
    public List<GoodsCategory> getGoodsCategoryList() {
        List<GoodsCategory> goodsCategoryList = shopGoodsMapper.getGoodsCategoryList();
        for (GoodsCategory goodsCategory : goodsCategoryList) {
            if (goodsCategory.parentId() != 0) {
                goodsCategory.setPic(qiNiuYunUtil.getDownloadUrl(goodsCategory.getPic()));
            }
        }
        return TreeUtil.generateTrees(goodsCategoryList);
    }

    @Override
    public List<ShopGoodsDto> getGoodsListByCategory(Integer id) {
        List<ShopGoodsDto> goodsListByCategory = shopGoodsMapper.getGoodsListByCategory(id);
        for (ShopGoodsDto shopGoodsDto : goodsListByCategory) {
            shopGoodsDto.setImage(qiNiuYunUtil.getDownloadUrl(shopGoodsDto.getImage()));
        }
        return goodsListByCategory;
    }

    @Override
    public List<ShopGoodsDto> getGoodsList(String search) {
        List<ShopGoodsDto> goodsList = shopGoodsMapper.getGoodsList(search);
        for (ShopGoodsDto shopGoodsDto : goodsList) {
            shopGoodsDto.setImage(qiNiuYunUtil.getDownloadUrl(shopGoodsDto.getImage()));
        }
        return goodsList;
    }
}
