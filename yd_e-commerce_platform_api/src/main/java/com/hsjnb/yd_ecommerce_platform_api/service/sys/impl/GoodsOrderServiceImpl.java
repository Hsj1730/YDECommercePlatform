package com.hsjnb.yd_ecommerce_platform_api.service.sys.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.CalculateInfoDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsOrderDetailDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsOrderManageDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.AppUser;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsOrder;
import com.hsjnb.yd_ecommerce_platform_api.mapper.app.ShopGoodsOrderMapper;
import com.hsjnb.yd_ecommerce_platform_api.mapper.sys.GoodsOrderMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.GoodsOrderService;
import com.hsjnb.yd_ecommerce_platform_api.utils.BarCodeGenerator;
import com.hsjnb.yd_ecommerce_platform_api.utils.QiNiuYunUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
 * @date : Created in 2022/05/17 1:33
 * @description :
 */

@Slf4j
@Service
public class GoodsOrderServiceImpl implements GoodsOrderService {

    private final GoodsOrderMapper goodsOrderMapper;

    private final ShopGoodsOrderMapper shopGoodsOrderMapper;

    private final QiNiuYunUtil qiNiuYunUtil;

    @Autowired
    public GoodsOrderServiceImpl(GoodsOrderMapper goodsOrderMapper, ShopGoodsOrderMapper shopGoodsOrderMapper, QiNiuYunUtil qiNiuYunUtil) {
        this.goodsOrderMapper = goodsOrderMapper;
        this.shopGoodsOrderMapper = shopGoodsOrderMapper;
        this.qiNiuYunUtil = qiNiuYunUtil;
    }

    @Override
    public GoodsOrderManageDto getGoodsOrderList(Map<String, Object> param) {
        PageHelper.startPage(param);
        Map<String,Object> map = JSONObject.parseObject(JSON.toJSONString(param.get("query")));
        String value = (String) map.get("value");
        String type = (String) map.get("type");
        String status = (String) param.get("status");
        GoodsOrderManageDto goodsOrderManageDto = new GoodsOrderManageDto();
        List<GoodsOrderDetailDto> goodsOrderList = goodsOrderMapper.getGoodsOrderList(value, type, status);
        for (GoodsOrderDetailDto goodsOrderDetailDto : goodsOrderList) {
            goodsOrderDetailDto.setImage(qiNiuYunUtil.getDownloadUrl(goodsOrderDetailDto.getImage()));
            goodsOrderDetailDto.setAttrImage(qiNiuYunUtil.getDownloadUrl(goodsOrderDetailDto.getAttrImage()));
        }
        goodsOrderManageDto.setTableData(new PageInfo<>(goodsOrderList));
        CalculateInfoDto calculateInfo = goodsOrderMapper.getCalculateInfo(value, type, status);
        if (calculateInfo == null) {
            calculateInfo = CalculateInfoDto
                    .builder()
                    .goodsNum(0)
                    .orderNum(0)
                    .userNum(0)
                    .orderPrice(new BigDecimal("0"))
                    .build();
        }
        goodsOrderManageDto.setCalculateInfo(calculateInfo);
        return goodsOrderManageDto;
    }

    @Override
    public Result saveRemark(GoodsOrder dto) {
        goodsOrderMapper.saveRemark(dto);
        return Result.success(null);
    }

    @Override
    public Result deleteGoodsOrder(Integer id) {
        GoodsOrder goodsOrder = shopGoodsOrderMapper.getGoodsOrder(id);
        AppUser user = shopGoodsOrderMapper.getUser(goodsOrder.getUserId());
        BigDecimal nowMoney = user.getNowMoney();
        BigDecimal payPrice = goodsOrder.getPayPrice();
        nowMoney = nowMoney.add(payPrice);
        user.setNowMoney(nowMoney);
        user.setPayCount(user.getPayCount() - 1);
        shopGoodsOrderMapper.updateUserPlay(user);
        shopGoodsOrderMapper.updateAttr(goodsOrder);
        shopGoodsOrderMapper.updateGoods2(goodsOrder);
        shopGoodsOrderMapper.delete(id);
        return Result.success(null);
    }

    @Override
    public Result getExpressList() {
        return Result.success(goodsOrderMapper.getExpressList());
    }

    @Override
    public synchronized Result saveExpress(GoodsOrder dto) {
        FutureTask<String> task;
        Callable<String> callable = () -> BarCodeGenerator.getC(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
        task = new FutureTask<>(callable);
        new Thread(task).start();
        try {
            dto.setDeliveryId(task.get());
            goodsOrderMapper.saveExpress(dto);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            log.error("物流编号生成失败");
            return Result.fail("物流编号生成失败");
        }
        return Result.success(null);
    }

    @Override
    public Result receipt(Integer id) {
        goodsOrderMapper.receipt(id);
        return Result.success(null);
    }

    @Override
    public Result success(Integer id) {
        goodsOrderMapper.success(id);
        return Result.success(null);
    }

}
