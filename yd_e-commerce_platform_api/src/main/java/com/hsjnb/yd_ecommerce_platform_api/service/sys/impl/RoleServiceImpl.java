package com.hsjnb.yd_ecommerce_platform_api.service.sys.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsjnb.yd_ecommerce_platform_api.dto.RoleDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.RoleMenuDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.Menu;
import com.hsjnb.yd_ecommerce_platform_api.entity.Role;
import com.hsjnb.yd_ecommerce_platform_api.mapper.MenuMapper;
import com.hsjnb.yd_ecommerce_platform_api.mapper.RoleMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.RoleService;
import com.hsjnb.yd_ecommerce_platform_api.utils.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
 * @date : Created in 2022/03/22 23:07
 * @description :
 */

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;

    private final MenuMapper menuMapper;

    private final TreeUtil treeUtil;

    @Autowired
    public RoleServiceImpl(RoleMapper roleMapper, MenuMapper menuMapper, TreeUtil treeUtil) {
        this.roleMapper = roleMapper;
        this.menuMapper = menuMapper;
        this.treeUtil = treeUtil;
    }

    @Override
    public PageInfo<Role> getRoleList(Map<String, Object> param) {
        PageHelper.startPage(param);
        return new PageInfo<>(roleMapper.getRoleList((String) param.get("name")));
    }

    @Override
    public Map<String, Object> getMenuTree(Integer id) {
        List<Menu> menuList = menuMapper.getMenuList();
        List<Menu> menus = treeUtil.buildTree(menuList);
        List<Integer> menuIds = roleMapper.getHaveMenu(id);
        Map<String,Object> result = new HashMap<>();
        result.put("menuTree",menus);
        result.put("menuIds",menuIds);
        return result;
    }

    @Override
    public Role getRoleById(Integer id) {
        return roleMapper.getRoleById(id);
    }

    @Override
    public int changeEnable(RoleDto dto) {
        return roleMapper.changeEnable(dto);
    }

    @Override
    public int deleteRole(Integer id) {
        roleMapper.deleteRole(id);
        roleMapper.deleteUserRoleByRoleId(id);
        return 1;
    }

    @Override
    public int addRole(RoleDto dto) {
        return roleMapper.addRole(dto);
    }

    @Override
    public int editRole(RoleDto dto) {
        return roleMapper.editRole(dto);
    }

    @Override
    public int saveRoleMenu(RoleMenuDto dto) {
        if (dto.getRoleId() == null || dto.getRoleId() == 0) {
            return 0;
        }
        // 先删掉之前的
        roleMapper.deleteRoleMenuById(dto.getRoleId());
        // 加入新的
        if (!dto.getMenuIds().isEmpty()) {
            roleMapper.insetRoleMenu(dto);
        }
        return 1;
    }
}
