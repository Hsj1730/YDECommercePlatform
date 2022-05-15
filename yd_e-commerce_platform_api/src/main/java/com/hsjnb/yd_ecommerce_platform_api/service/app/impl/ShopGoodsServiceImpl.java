package com.hsjnb.yd_ecommerce_platform_api.service.app.impl;

import cn.hutool.core.util.ObjectUtil;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsAttrValueDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsDetailDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.SearchTipDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.ShopGoodsDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsCategory;
import com.hsjnb.yd_ecommerce_platform_api.mapper.app.ShopGoodsMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.app.ShopGoodsService;
import com.hsjnb.yd_ecommerce_platform_api.utils.PropertyUtil;
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

    @Autowired
    public ShopGoodsServiceImpl(ShopGoodsMapper shopGoodsMapper, QiNiuYunUtil qiNiuYunUtil) {
        this.shopGoodsMapper = shopGoodsMapper;
        this.qiNiuYunUtil = qiNiuYunUtil;
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

    @Override
    public Result getGoodsDetail(Integer id) {
        if (ObjectUtil.isNull(id)) {
            return Result.fail("商品不存在");
        }
        // 获取商品详情
        GoodsDetailDto goodsDetail = shopGoodsMapper.getGoodsDetail(id);
        goodsDetail.setImage(qiNiuYunUtil.getDownloadUrl(goodsDetail.getImage()));
        String[] sliderImages = goodsDetail.getSliderImage().split(",");
        StringBuilder sliderUrl = new StringBuilder();
        for (int i = 0; i < sliderImages.length; i++) {
            sliderUrl.append(qiNiuYunUtil.getDownloadUrl(sliderImages[i]));
            if (i < sliderImages.length - 1) {
                sliderUrl.append(",");
            }
        }
        goodsDetail.setSliderImage(sliderUrl.toString());
        // 获取商品属性
        List<GoodsAttrValueDto> goodsAttrValue = shopGoodsMapper.getGoodsAttrValue(id);
        for (GoodsAttrValueDto goodsAttrValueDto : goodsAttrValue) {
            goodsAttrValueDto.setImage(qiNiuYunUtil.getDownloadUrl(goodsAttrValueDto.getImage()));
            goodsAttrValueDto.setBarCode(goodsDetail.getBarCode() + goodsAttrValueDto.getBarCode());
        }
        goodsDetail.setGoodsAttrValueDtoList(goodsAttrValue);
        return Result.success(goodsDetail);
    }

    @Override
    public List<SearchTipDto> getGoodsListTip(String search) {
        List<SearchTipDto> goodsListTip = shopGoodsMapper.getGoodsListTip(search);
        if (goodsListTip != null) {
            String backendUrl = PropertyUtil.getProperty("backendUrl");
            for (SearchTipDto searchTipDto : goodsListTip) {
                searchTipDto.setLink(backendUrl + "app/goods/getGoodsList?search=" + searchTipDto.getKeyword());
            }
        }
        return goodsListTip;
    }
}
