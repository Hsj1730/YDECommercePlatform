package com.hsjnb.yd_ecommerce_platform_api.service.sys.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsjnb.yd_ecommerce_platform_api.entity.Goods;
import com.hsjnb.yd_ecommerce_platform_api.mapper.sys.GoodsMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.GoodsService;
import com.hsjnb.yd_ecommerce_platform_api.utils.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * @date : Created in 2022/03/30 21:32
 * @description :
 */

@Service
public class GoodsServiceImpl implements GoodsService {

    private final GoodsMapper goodsMapper;

    @Autowired
    public GoodsServiceImpl(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    @Override
    public PageInfo<Goods> getGoodsList(Map<String, Object> param) {
        PageHelper.startPage(param);
        Map<String, String> query = DateFormatUtil.getObjectToMap(param.get("query"));
        assert query != null;
        List<Goods> goodsList = goodsMapper.getGoodsList(query.get("value"), query.get("type"), (String) param.get("isShow"));
        return new PageInfo<>(goodsList);
    }

    @Override
    public void setGoodsIsShow(Integer id, String status) {
        goodsMapper.setGoodsIsShow(id,status);
    }

}
