package com.hsjnb.yd_ecommerce_platform_api.mapper.sys;

import com.hsjnb.yd_ecommerce_platform_api.dto.MaterialDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.Material;
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
 * @date : Created in 2022/03/29 20:23
 * @description :
 */

@Mapper
@Repository
public interface MaterialMapper {

    List<MaterialDto> getMaterialList(@Param("groupId") Integer groupId);

    void addMaterial(@Param("material") Material material);

    void deleteMaterial(@Param("dto") MaterialDto dto);

    void deleteMaterialByGroupId(@Param("groupId") Integer id);

    MaterialDto getMaterialByKey(@Param("url") String key);

}
