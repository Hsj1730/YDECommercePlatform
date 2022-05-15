package com.hsjnb.yd_ecommerce_platform_api.service.app;

import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.SearchTipDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.ShopGoodsDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsCategory;

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

public interface ShopGoodsService {

    List<GoodsCategory> getGoodsCategoryList();

    List<ShopGoodsDto> getGoodsListByCategory(Integer id);

    List<ShopGoodsDto> getGoodsList(String search);

    Result getGoodsDetail(Integer id);

    List<SearchTipDto> getGoodsListTip(String search);

}
