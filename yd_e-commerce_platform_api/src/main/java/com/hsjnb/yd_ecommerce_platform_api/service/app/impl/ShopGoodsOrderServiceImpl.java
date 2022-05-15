package com.hsjnb.yd_ecommerce_platform_api.service.app.impl;

import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.*;
import com.hsjnb.yd_ecommerce_platform_api.entity.AppUser;
import com.hsjnb.yd_ecommerce_platform_api.entity.ConsigneeAddress;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsAttrValue;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsOrder;
import com.hsjnb.yd_ecommerce_platform_api.mapper.app.ShopGoodsOrderMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.app.ShopGoodsOrderService;
import com.hsjnb.yd_ecommerce_platform_api.utils.BarCodeGenerator;
import com.hsjnb.yd_ecommerce_platform_api.utils.PropertyUtil;
import com.hsjnb.yd_ecommerce_platform_api.utils.QiNiuYunUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
 * @date : Created in 2022/05/15 12:14
 * @description :
 */

@Slf4j
@Service
public class ShopGoodsOrderServiceImpl implements ShopGoodsOrderService {

    private final ShopGoodsOrderMapper shopGoodsOrderMapper;

    private final QiNiuYunUtil qiNiuYunUtil;

    @Autowired
    public ShopGoodsOrderServiceImpl(ShopGoodsOrderMapper shopGoodsOrderMapper, QiNiuYunUtil qiNiuYunUtil) {
        this.shopGoodsOrderMapper = shopGoodsOrderMapper;
        this.qiNiuYunUtil = qiNiuYunUtil;
    }

    @Override
    public Result addGoodsOrder(GoodsOrderAddDto goodsOrderAddDto) {
        goodsOrderAddDto.setIsNew("1");
        GoodsOrderAddDto goodsPrice = shopGoodsOrderMapper.getGoodsPrice(goodsOrderAddDto);
        goodsOrderAddDto.setTotalPrice(goodsPrice.getTotalPrice());
        goodsOrderAddDto.setTotalPostage(goodsPrice.getTotalPostage());
        List<GoodsOrderAddDto> goodsOrderAddDtoList = new ArrayList<>();
        goodsOrderAddDtoList.add(goodsOrderAddDto);
        return addOrder(goodsOrderAddDtoList,true);
    }

    @Override
    public synchronized Result addOrder(List<GoodsOrderAddDto> goodsOrderAddDtoList,boolean isNew) {
        BigDecimal price = new BigDecimal("0.00");
        for (GoodsOrderAddDto goodsOrderAddDto : goodsOrderAddDtoList) {
            GoodsAttrValue goodsStock = shopGoodsOrderMapper.getGoodsStock(goodsOrderAddDto);
            if (goodsStock != null) {
                if (goodsStock.getStock() < goodsOrderAddDto.getTotalNum()) {
                    String storeName = shopGoodsOrderMapper.getStoreName(goodsOrderAddDto);
                    return Result.fail(storeName + "当前属性的商品库存不充足");
                } else {
                    BigDecimal totalPrice = goodsOrderAddDto.getTotalPrice();
                    BigDecimal totalNum = new BigDecimal(goodsOrderAddDto.getTotalNum().toString());
                    BigDecimal totalPostage = goodsOrderAddDto.getTotalPostage();
                    BigDecimal multiply1 = totalPrice.multiply(totalNum);
                    BigDecimal multiply2 = totalPostage.multiply(totalNum);
                    goodsOrderAddDto.setPayPostage(multiply2);
                    BigDecimal add = multiply1.add(multiply2);
                    goodsOrderAddDto.setPayPrice(add);
                    price = price.add(add);
                }
            } else {
                return Result.fail("商品和商品属性不存在");
            }
        }
        String playType = goodsOrderAddDtoList.get(0).getPlayType();
        AppUser user = shopGoodsOrderMapper.getUser(goodsOrderAddDtoList.get(0).getUserId());
        BigDecimal nowMoney = user.getNowMoney();
        if (playType.equals("2")) {
            if (nowMoney.compareTo(price) < 0) {
                return Result.fail("用户余额不足");
            }
        }
        if (isNew) {
            for (GoodsOrderAddDto goodsOrderAddDto : goodsOrderAddDtoList) {
                shopGoodsOrderMapper.addGoodsCart(goodsOrderAddDto);
            }
        }
        for (GoodsOrderAddDto goodsOrderAddDto : goodsOrderAddDtoList) {
            FutureTask<String> task;
            Callable<String> callable = () -> BarCodeGenerator.getC(StringUtils.replace(UUID.randomUUID().toString(), "-", ""));
            task = new FutureTask<>(callable);
            new Thread(task).start();
            try {
                goodsOrderAddDto.setOrderId(task.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                log.error("订单生成失败");
                return Result.fail("订单生成失败");
            }
            ConsigneeAddress address = shopGoodsOrderMapper.getAddress(goodsOrderAddDto);
            goodsOrderAddDto.setRealName(address.getRealName());
            goodsOrderAddDto.setPhone(address.getPhone());
            goodsOrderAddDto.setAddress(address.getProvince() + address.getCity() + address.getDistrict() + address.getDetail());
            goodsOrderAddDto.setPayStatus("1");
            goodsOrderAddDto.setStatus("0");
            shopGoodsOrderMapper.addOrder(goodsOrderAddDto);
            shopGoodsOrderMapper.updateGoods(goodsOrderAddDto);
            shopGoodsOrderMapper.updateGoodsAttrValue(goodsOrderAddDto);
            if (playType.equals("2")) {
                shopGoodsOrderMapper.updateIsPlay(goodsOrderAddDto);
            }
        }
        if (playType.equals("2")) {
            nowMoney = nowMoney.subtract(price);
        }
        user.setPayCount(user.getPayCount() + goodsOrderAddDtoList.size());
        user.setNowMoney(nowMoney);
        shopGoodsOrderMapper.updateUserPlay(user);
        return Result.success(null);
    }

    @Override
    public Result getGoodsOrderList(GoodsOrderSearchDto dto) {
        List<GoodsOrderResultDto> goodsOrderList = shopGoodsOrderMapper.getGoodsOrderList(dto);
        if (goodsOrderList != null) {
            String backendUrl = PropertyUtil.getProperty("backendUrl");
            for (GoodsOrderResultDto goodsOrderResultDto : goodsOrderList) {
                goodsOrderResultDto.setImage(qiNiuYunUtil.getDownloadUrl(goodsOrderResultDto.getImage()));
                goodsOrderResultDto.setLink(backendUrl + "app/goodsOrder/getGoodsDetail/" + goodsOrderResultDto.getGoodsId());
            }
        }
        return Result.success(goodsOrderList);
    }

    @Override
    public Result updateStatus(Integer id, String status) {
        switch (status) {
            case "0":
                GoodsOrder goodsOrder = shopGoodsOrderMapper.getGoodsOrder(id);
                AppUser user = shopGoodsOrderMapper.getUser(id);
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
            case "1":
                shopGoodsOrderMapper.updateStatus(id, "2");
                return Result.success(null);
            case "2":
                shopGoodsOrderMapper.updateStatus(id, "3");
                return Result.success(null);
        }
        return null;
    }

    @Override
    public Result evaluate(GoodsOrderEvaluateDto dto) {
        shopGoodsOrderMapper.evaluate(dto);
        return Result.success(null);
    }

    @Override
    public Result getGoodsOrderDetail(Integer id) {
        GoodsOrderResultDetailDto goodsOrderDetail = shopGoodsOrderMapper.getGoodsOrderDetail(id);
        if (goodsOrderDetail != null) {
            goodsOrderDetail.setImage(qiNiuYunUtil.getDownloadUrl(goodsOrderDetail.getImage()));
            String backendUrl = PropertyUtil.getProperty("backendUrl");
            goodsOrderDetail.setLink(backendUrl + "app/goodsOrder/getGoodsDetail/" + goodsOrderDetail.getGoodsId());
        }
        return Result.success(goodsOrderDetail);
    }
}
