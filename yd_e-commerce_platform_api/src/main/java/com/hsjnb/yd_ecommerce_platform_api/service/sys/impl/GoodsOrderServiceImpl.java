package com.hsjnb.yd_ecommerce_platform_api.service.sys.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsOrderManageDto;
import com.hsjnb.yd_ecommerce_platform_api.mapper.sys.GoodsOrderMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.GoodsOrderService;
import com.hsjnb.yd_ecommerce_platform_api.utils.QiNiuYunUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * @date : Created in 2022/05/17 1:33
 * @description :
 */

@Service
public class GoodsOrderServiceImpl implements GoodsOrderService {

    private final GoodsOrderMapper goodsOrderMapper;

    private final QiNiuYunUtil qiNiuYunUtil;

    @Autowired
    public GoodsOrderServiceImpl(GoodsOrderMapper goodsOrderMapper, QiNiuYunUtil qiNiuYunUtil) {
        this.goodsOrderMapper = goodsOrderMapper;
        this.qiNiuYunUtil = qiNiuYunUtil;
    }

    @Override
    public PageInfo<GoodsOrderManageDto> getGoodsOrderList(Map<String, Object> param) {
        PageHelper.startPage(param);
        Map<String,Object> map = JSONObject.parseObject(JSON.toJSONString(param.get("query")));
        String value = (String) map.get("value");
        String key = (String) map.get("key");
        String status = (String) param.get("status");
        return null;
    }
}
