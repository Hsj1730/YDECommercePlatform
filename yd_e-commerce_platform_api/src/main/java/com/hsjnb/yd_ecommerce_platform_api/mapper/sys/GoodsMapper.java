package com.hsjnb.yd_ecommerce_platform_api.mapper.sys;

import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.Goods;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsAttr;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsAttrResult;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsAttrValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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
 * @date : Created in 2022/03/30 21:33
 * @description :
 */

@Mapper
@Repository
public interface GoodsMapper {

    List<Goods> getGoodsList(@Param("value") String value, @Param("type") String type, @Param("isShow") String isShow);

    void setGoodsIsShow(@Param("id") Integer id,@Param("status") String status);

    GoodsDto getGoodsInfo(@Param("id") Integer id);

    void addGoods(@Param("dto") GoodsDto dto);

    void editGoods(@Param("dto") GoodsDto dto);

    void deleteGoods(@Param("id") Integer id);

    String getGoodsAttrResult(@Param("goodsId") Integer id);

    void updateGoodsByAttr(@Param("goods") Goods goods);

    void deleteGoodsAttr(@Param("goodsId") Integer id);

    void deleteGoodsAttrValue(@Param("goodsId") Integer id);

    void deleteGoodsAttrResult(@Param("goodsId") Integer id);

    void saveGoodsAttr(@Param("attrGroup") List<GoodsAttr> attrGroup);

    void saveGoodsAttrValue(@Param("valueGroup") List<GoodsAttrValue> valueGroup);

    void saveGoodsAttrResult(@Param("goodsAttrResult") GoodsAttrResult goodsAttrResult);

}
