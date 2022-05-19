package com.hsjnb.yd_ecommerce_platform_api.mapper.app;

import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsCartDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsOrderAddDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.GoodsCart;
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
 * @date : Created in 2022/05/15 10:10
 * @description :
 */

@Mapper
@Repository
public interface GoodsCartMapper {

    List<GoodsCartDto> getGoodsCartList(@Param("userId") Integer id);

    void addGoodsCart(@Param("goodsCart")GoodsCart goodsCart);

    GoodsCart queryExist(@Param("goodsCart")GoodsCart goodsCart);

    void delGoodsCart(@Param("id") Integer id);

    void updateGoodsCartGoodsNum(@Param("id") Integer id,@Param("num") Integer num);

    GoodsOrderAddDto getGoodsCart(@Param("dto") GoodsOrderAddDto dto);

    void updateGoodsCart(@Param("dto")GoodsCart goodsCart);

}
