package com.hsjnb.yd_ecommerce_platform_api.mapper.app;

import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsAttrValueDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsDetailDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.SearchTipDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.ShopGoodsDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsAttr;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsCategory;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
 * @date : Created in 2022/05/09 23:35
 * @description :
 */

@Mapper
@Repository
public interface ShopGoodsMapper {

    List<GoodsCategory> getGoodsCategoryList();

    List<ShopGoodsDto> getGoodsListByCategory(@Param("id") Integer id);

    List<ShopGoodsDto> getGoodsList(@Param("search") String search);

   GoodsDetailDto getGoodsDetail(@Param("id") Integer id);

    List<GoodsAttrValueDto> getGoodsAttrValue(@Param("goodsId") Integer id);

    List<SearchTipDto> getGoodsListTip(@Param("search") String search);

    List<GoodsAttr> getGoodsAttr(@Param("id") Integer id);

}
