package com.hsjnb.yd_ecommerce_platform_api.service.app;

import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsOrderAddDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsOrderEvaluateDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsOrderSearchDto;

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
 * @date : Created in 2022/05/15 12:13
 * @description :
 */

public interface ShopGoodsOrderService {

    Result addGoodsOrder(GoodsOrderAddDto goodsOrderAddDto);

    Result addOrder(List<GoodsOrderAddDto> goodsOrderAddDtoList, boolean isNew);

    Result getGoodsOrderList(GoodsOrderSearchDto dto);

    Result updateStatus(Integer id,String status);

    Result evaluate(GoodsOrderEvaluateDto dto);

    Result getGoodsOrderDetail(Integer id);

}
