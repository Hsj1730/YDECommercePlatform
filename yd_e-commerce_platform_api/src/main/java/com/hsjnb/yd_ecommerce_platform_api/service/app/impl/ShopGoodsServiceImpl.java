package com.hsjnb.yd_ecommerce_platform_api.service.app.impl;

import cn.hutool.core.util.ObjectUtil;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsAttrValueDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsDetailDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.SearchTipDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.ShopGoodsDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsAttr;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsCategory;
import com.hsjnb.yd_ecommerce_platform_api.mapper.app.ShopGoodsMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.app.ShopGoodsService;
import com.hsjnb.yd_ecommerce_platform_api.utils.PropertyUtil;
import com.hsjnb.yd_ecommerce_platform_api.utils.QiNiuYunUtil;
import com.hsjnb.yd_ecommerce_platform_api.utils.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        goodsDetail.setSku(sku(goodsDetail));
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

    public Map<String,Object> sku(GoodsDetailDto goodsDetail) {
        Map<String,Object> result = new HashMap<>();
        List<GoodsAttr> goodsAttr = shopGoodsMapper.getGoodsAttr(goodsDetail.getId());
        List<Map<String,Object>> tree = new ArrayList<>();
        for (int i = 0; i < goodsAttr.size(); i++) {
            Map<String,Object> treeMap = new HashMap<>();
            treeMap.put("k",goodsAttr.get(i).getAttrName());
            treeMap.put("k_s",goodsAttr.get(i).getId());
            List<Map<String,Object>> l1 = new ArrayList<>();
            String[] split = goodsAttr.get(i).getAttrValues().split(",");
            for (int j = 0; j < split.length; j++) {
                Map<String,Object> m1 = new HashMap<>();
                m1.put("name",split[j]);
                m1.put("id",j + 1);
                if (i == 0) {
                    m1.put("imgUrl",goodsDetail.getImage());
                    m1.put("previewImgUrl",goodsDetail.getImage());
                }
                l1.add(m1);
            }
            treeMap.put("v",l1);
            treeMap.put("largeImageMode",true);
            tree.add(treeMap);
        }
        result.put("tree",tree);
        List<GoodsAttrValueDto> goodsAttrValueDtoList = goodsDetail.getGoodsAttrValueDtoList();
        List<Map<String,Object>> list = new ArrayList<>();
        for (GoodsAttrValueDto goodsAttrValueDto : goodsAttrValueDtoList) {
            Map<String,Object> listMap = new HashMap<>();
            listMap.put("id",goodsAttrValueDto.getId());
            listMap.put("price",goodsAttrValueDto.getPrice());
            listMap.put("stock_num",goodsAttrValueDto.getStock());
            listMap.put("image",goodsAttrValueDto.getImage());
            listMap.put("unique",goodsAttrValueDto.getUnique());
            listMap.put("barCode",goodsAttrValueDto.getBarCode());
            listMap.put("cost",goodsAttrValueDto.getCost());
            listMap.put("sales",goodsAttrValueDto.getSales());
            listMap.put("sku",goodsAttrValueDto.getSku());
            String[] split = goodsAttrValueDto.getSku().split(",");
            for (String s : split) {
                for (GoodsAttr attr : goodsAttr) {
                    String[] strings = attr.getAttrValues().split(",");
                    for (int i = 0; i < strings.length; i++) {
                        if (s.equals(strings[i])) {
                            listMap.put(attr.getId().toString(),++i);
                        }
                    }
                }
            }
            list.add(listMap);
        }
        result.put("list",list);
        result.put("price",goodsDetail.getPrice());
        result.put("stock_num",goodsDetail.getStock());
        result.put("collection_id","");
        result.put("none_sku",false);
        result.put("messages",null);
        result.put("hide_stock",false);
        return result;
    }
}
