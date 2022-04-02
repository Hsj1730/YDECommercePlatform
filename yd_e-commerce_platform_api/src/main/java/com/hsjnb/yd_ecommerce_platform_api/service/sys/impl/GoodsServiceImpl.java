package com.hsjnb.yd_ecommerce_platform_api.service.sys.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.MaterialDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.Goods;
import com.hsjnb.yd_ecommerce_platform_api.mapper.sys.GoodsCategoryMapper;
import com.hsjnb.yd_ecommerce_platform_api.mapper.sys.GoodsMapper;
import com.hsjnb.yd_ecommerce_platform_api.mapper.sys.MaterialMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.GoodsService;
import com.hsjnb.yd_ecommerce_platform_api.utils.BarCodeGenerator;
import com.hsjnb.yd_ecommerce_platform_api.utils.QiNiuYunUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

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
 * @date : Created in 2022/03/30 21:32
 * @description :
 */

@Slf4j
@Service
public class GoodsServiceImpl implements GoodsService {

    private final GoodsMapper goodsMapper;

    private final GoodsCategoryMapper goodsCategoryMapper;

    private final QiNiuYunUtil qiNiuYunUtil;

    private final MaterialMapper materialMapper;

    @Autowired
    public GoodsServiceImpl(GoodsMapper goodsMapper,GoodsCategoryMapper goodsCategoryMapper,
                            QiNiuYunUtil qiNiuYunUtil,MaterialMapper materialMapper) {
        this.goodsMapper = goodsMapper;
        this.goodsCategoryMapper = goodsCategoryMapper;
        this.qiNiuYunUtil = qiNiuYunUtil;
        this.materialMapper = materialMapper;
    }

    @Override
    public PageInfo<Goods> getGoodsList(Map<String, Object> param) {
        PageHelper.startPage(param);
        Map<String,Object> map = JSONObject.parseObject(JSON.toJSONString(param.get("query")));
        List<Goods> goodsList = goodsMapper.getGoodsList((String) map.get("value"), (String) map.get("type"), (String) param.get("isShow"));
        for (Goods goods : goodsList) {
            goods.setImage(qiNiuYunUtil.getDownloadUrl(goods.getImage()));
        }
        return new PageInfo<>(goodsList);
    }

    @Override
    public void setGoodsIsShow(Integer id, String status) {
        goodsMapper.setGoodsIsShow(id,status);
    }

    @Override
    public GoodsDto getGoodsInfo(Integer id) {
        GoodsDto goods = goodsMapper.getGoodsInfo(id);
        // 获取商品得图片资源信息
        MaterialDto imageByKey = materialMapper.getMaterialByKey(goods.getImage());
        imageByKey.setRealUrl(qiNiuYunUtil.getDownloadUrl(imageByKey.getUrl()));
        MaterialDto[] imageArr = {imageByKey};
        String sliderImage = goods.getSliderImage();
        String[] split = sliderImage.split(",");
        MaterialDto[] sliderImageArr = new MaterialDto[split.length];
        for (int i = 0; i < split.length; i++) {
            MaterialDto sliderImageByKey = materialMapper.getMaterialByKey(split[i]);
            sliderImageByKey.setRealUrl(qiNiuYunUtil.getDownloadUrl(sliderImageByKey.getUrl()));
            sliderImageArr[i] = sliderImageByKey;
        }
        goods.setImageArr(imageArr);
        goods.setSliderImageArr(sliderImageArr);
        return goods;
    }

    @Override
    public Result saveGoodsInfo(GoodsDto dto) {
        // 判断分类是否为二级分类
        if (goodsCategoryMapper.checkCategory(dto.getCateId()) == 0) {
            return Result.fail("商品分类只能为二级分类");
        }
        if (dto.getId() != null) { // 修改
            goodsMapper.editGoods(dto);
        } else { // 添加
            FutureTask<String> task;
            Callable<String> callable = () -> BarCodeGenerator.getD(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
            task = new FutureTask<>(callable);
            new Thread(task).start();
            try {
                dto.setBarCode(task.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                log.error("商品码生成失败");
                return Result.fail("商品码生成失败");
            }
            goodsMapper.addGoods(dto);
        }
        return Result.success(null);
    }

}
