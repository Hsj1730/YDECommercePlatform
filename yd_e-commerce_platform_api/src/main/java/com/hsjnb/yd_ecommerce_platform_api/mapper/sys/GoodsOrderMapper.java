package com.hsjnb.yd_ecommerce_platform_api.mapper.sys;

import com.hsjnb.yd_ecommerce_platform_api.dto.CalculateInfoDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.GoodsOrderDetailDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.Express;
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
 * @date : Created in 2022/05/17 1:32
 * @description :
 */

@Mapper
@Repository
public interface GoodsOrderMapper {

    CalculateInfoDto getCalculateInfo(@Param("value") String value, @Param("type") String type, @Param("status") String status);

    List<GoodsOrderDetailDto> getGoodsOrderList(@Param("value") String value, @Param("type") String type, @Param("status") String status);

    void saveRemark(@Param("dto") GoodsOrder dto);

    List<Express> getExpressList();

    void saveExpress(@Param("dto") GoodsOrder dto);

    void receipt(@Param("id") Integer id);

    void success(@Param("id") Integer id);

}
