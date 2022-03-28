package com.hsjnb.yd_ecommerce_platform_api.service.sys.impl;

import cn.hutool.core.util.StrUtil;
import com.hsjnb.yd_ecommerce_platform_api.dto.MenuDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.Menu;
import com.hsjnb.yd_ecommerce_platform_api.mapper.MenuMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.MenuService;
import com.hsjnb.yd_ecommerce_platform_api.utils.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
 * @date : Created in 2022/03/21 21:56
 * @description :
 */

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;

    private final TreeUtil treeUtil;

    @Autowired
    public MenuServiceImpl(MenuMapper menuMapper, TreeUtil treeUtil) {
        this.menuMapper = menuMapper;
        this.treeUtil = treeUtil;
    }

    @Override
    public List<Menu> getMenuList() {
        List<Menu> menuList = menuMapper.getMenuList();
        return treeUtil.buildTree(menuList);
    }

    @Override
    public Menu getMenuById(Integer id) {
        Menu menu = menuMapper.getMenuById(id);
        return menu;
    }

    @Override
    public int changeEnable(MenuDto dto) {
        return menuMapper.changeEnable(dto);
    }

    @Override
    public int changeHidden(MenuDto dto) {
        return menuMapper.changeHidden(dto);
    }

    @Override
    public boolean deleteMenu(Integer id) {
        if (menuMapper.queryChildren(id) != 0) {
            return false;
        }
        menuMapper.deleteMenu(id);  // 删除菜单
        menuMapper.deleteRoleMenuByMenuId(id);  // 删除角色菜单
        return true;
    }

    @Override
    public int addMenu(MenuDto dto) {
        if (StrUtil.isEmpty(dto.getIcon())) {
            dto.setIcon("list");
        }
        dto.setType(StrUtil.isEmpty(dto.getPath()) ? "1" : "2");
        if (StrUtil.isEmpty(dto.getPath())) {
            String path = UUID.randomUUID().toString().replaceAll("-", "");
            dto.setPath(path);
        }
        return menuMapper.addMenu(dto);
    }

    @Override
    public int editMenu(MenuDto dto) {
        if (StrUtil.isEmpty(dto.getIcon())) {
            dto.setIcon("el-icon-menu");
        }
        if (dto.getParentId() == null) {
            dto.setParentId(0);
        }
        dto.setType(StrUtil.isEmpty(dto.getPath()) ? "1" : "2");
        if (StrUtil.isEmpty(dto.getPath())) {
            String path = UUID.randomUUID().toString().replaceAll("-", "");
            dto.setPath(path);
        }
        return menuMapper.editMenu(dto);
    }
}
