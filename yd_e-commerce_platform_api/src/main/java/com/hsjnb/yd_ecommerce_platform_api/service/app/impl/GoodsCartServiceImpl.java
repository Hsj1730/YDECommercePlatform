package com.hsjnb.yd_ecommerce_platform_api.service.app.impl;

import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsCartDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsOrderAddDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsCart;
import com.hsjnb.yd_ecommerce_platform_api.mapper.app.GoodsCartMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.app.GoodsCartService;
import com.hsjnb.yd_ecommerce_platform_api.service.app.ShopGoodsOrderService;
import com.hsjnb.yd_ecommerce_platform_api.utils.PropertyUtil;
import com.hsjnb.yd_ecommerce_platform_api.utils.QiNiuYunUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
 * @date : Created in 2022/05/15 10:11
 * @description :
 */

@Service
public class GoodsCartServiceImpl implements GoodsCartService {

    private final GoodsCartMapper goodsCartMapper;

    private final ShopGoodsOrderService shopGoodsOrderService;

    private final QiNiuYunUtil qiNiuYunUtil;

    @Autowired
    public GoodsCartServiceImpl(GoodsCartMapper goodsCartMapper, ShopGoodsOrderService shopGoodsOrderService, QiNiuYunUtil qiNiuYunUtil) {
        this.goodsCartMapper = goodsCartMapper;
        this.shopGoodsOrderService = shopGoodsOrderService;
        this.qiNiuYunUtil = qiNiuYunUtil;
    }

    @Override
    public Result getGoodsCartList(Integer id) {
        if (id == null) {
            return Result.fail("用户不存在");
        }
        List<GoodsCartDto> goodsCartList = goodsCartMapper.getGoodsCartList(id);
        if (goodsCartList != null) {
            String backendUrl = PropertyUtil.getProperty("backendUrl");
            for (GoodsCartDto goodsCartDto : goodsCartList) {
                goodsCartDto.setImage(qiNiuYunUtil.getDownloadUrl(goodsCartDto.getImage()));
                goodsCartDto.setLink(backendUrl + "app/goodsCart/getGoodsDetail/" + goodsCartDto.getGoodsId());
            }
        }
        return Result.success(goodsCartList);
    }

    @Override
    public Result addGoodsCart(GoodsCart goodsCart) {
        if (goodsCart.getUserId() == null) {
            return Result.fail("用户不存在");
        }
        if (goodsCart.getGoodsId() == null) {
            return Result.fail("商品不存在");
        }
        if (goodsCart.getGoodsAttrUnique() == null) {
            return Result.fail("商品属性不存在");
        }
        if (goodsCart.getNum() == null) {
            return Result.fail("商品数量错误");
        }
        goodsCartMapper.addGoodsCart(goodsCart);
        return Result.success(null);
    }

    @Override
    public Result delGoodsCart(String cartStr) {
        String[] split = cartStr.split(",");
        for (String s : split) {
            goodsCartMapper.delGoodsCart(Integer.parseInt(s));
        }
        return Result.success(null);
    }

    @Override
    public Result updateGoodsCartGoodsNum(Integer id, Integer num) {
        goodsCartMapper.updateGoodsCartGoodsNum(id,num);
        return Result.success(null);
    }

    @Override
    public Result addGoodsOrder(GoodsOrderAddDto dto) {
        List<GoodsOrderAddDto> goodsOrderAddDtoList = new ArrayList<>();
        for (Integer cartId : dto.getCartIdArr()) {
            dto.setCartId(cartId);
            goodsOrderAddDtoList.add(goodsCartMapper.getGoodsCart(dto));
        }
        return shopGoodsOrderService.addOrder(goodsOrderAddDtoList,false);
    }
}
