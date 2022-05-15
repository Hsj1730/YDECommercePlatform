package com.hsjnb.yd_ecommerce_platform_api.mapper.app;

import com.hsjnb.yd_ecommerce_platform_api.dto.*;
import com.hsjnb.yd_ecommerce_platform_api.entity.AppUser;
import com.hsjnb.yd_ecommerce_platform_api.entity.ConsigneeAddress;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsAttrValue;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsOrder;
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
 * @date : Created in 2022/05/15 12:12
 * @description :
 */

@Mapper
@Repository
public interface ShopGoodsOrderMapper {

    GoodsAttrValue getGoodsStock(@Param("dto") GoodsOrderAddDto dto);

    String getStoreName(@Param("dto") GoodsOrderAddDto dto);

    AppUser getUser(@Param("id") Integer id);

    Integer addGoodsCart(@Param("dto") GoodsOrderAddDto dto);

    void updateUserPlay(@Param("user") AppUser appUser);

    ConsigneeAddress getAddress(@Param("dto") GoodsOrderAddDto dto);

    void addOrder(@Param("dto") GoodsOrderAddDto dto);

    void updateGoods(@Param("dto") GoodsOrderAddDto dto);

    void updateGoodsAttrValue(@Param("dto") GoodsOrderAddDto dto);

    void updateIsPlay(@Param("dto") GoodsOrderAddDto dto);

    GoodsOrderAddDto getGoodsPrice(@Param("dto") GoodsOrderAddDto dto);

    List<GoodsOrderResultDto> getGoodsOrderList(@Param("dto") GoodsOrderSearchDto dto);

    void updateStatus(@Param("id") Integer id,@Param("status") String status);

    GoodsOrder getGoodsOrder(@Param("id") Integer id);

    void updateAttr(@Param("dto") GoodsOrder dto);

    void updateGoods2(@Param("dto") GoodsOrder dto);

    void delete(@Param("id") Integer id);

    void evaluate(@Param("dto") GoodsOrderEvaluateDto dto);

    GoodsOrderResultDetailDto getGoodsOrderDetail(@Param("id") Integer id);

}
