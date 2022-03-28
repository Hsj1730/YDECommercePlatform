package com.hsjnb.yd_ecommerce_platform_api.mapper;

import com.hsjnb.yd_ecommerce_platform_api.dto.RoleDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.RoleMenuDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.Role;
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
 * @date : Created in 2022/03/22 23:07
 * @description :
 */

@Mapper
@Repository
public interface RoleMapper {

    List<Role> queryRoleList();

    List<Role> getRoleList(@Param("name") String name);

    List<Integer> getHaveMenu(@Param("roleId") Integer id);

    Role getRoleById(@Param("id") Integer id);

    int changeEnable(@Param("dto") RoleDto dto);

    int deleteRole(@Param("id") Integer id);

    int deleteUserRoleByRoleId(@Param("id") Integer id);

    int addRole(@Param("dto") RoleDto dto);

    int editRole(RoleDto dto);

    int deleteRoleMenuById(@Param("id") Integer id);

    int insetRoleMenu(@Param("dto") RoleMenuDto roleMenuDto);

}
