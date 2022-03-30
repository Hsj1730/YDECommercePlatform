package com.hsjnb.yd_ecommerce_platform_api.mapper.sys;

import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsCategoryDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsCategory;
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
 * @date : Created in 2022/03/28 11:28
 * @description :
 */

@Mapper
@Repository
public interface GoodsCategoryMapper {

    List<GoodsCategory> getGoodsCategoryList();

    void addGoodsCategory(@Param("goodsCategory") GoodsCategory goodsCategory);

    GoodsCategoryDto getGoodsCategoryById(@Param("id") Integer id);

    void editGoodsCategory(@Param("dto") GoodsCategoryDto dto);

    Integer checkCategory(@Param("parentId") Integer parentId);

    Integer getGoodsCategoryChildren(@Param("parentId") Integer id);

    Integer getGoods(@Param("cateId") Integer id);

    void deleteGoodsCategory(@Param("id") Integer id);

}
