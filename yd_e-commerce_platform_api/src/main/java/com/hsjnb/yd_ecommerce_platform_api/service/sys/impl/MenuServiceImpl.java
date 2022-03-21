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
        return menuMapper.getMenuById(id);
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
        menuMapper.deleteMenu(id);  // 删除菜单
        menuMapper.deleteRoleMenuByMenuId(id);  // 删除角色菜单
        return true;
    }

    @Override
    public int addMenu(MenuDto dto) {
        if (StrUtil.isEmpty(dto.getIcon())) {
            dto.setIcon("el-icon-menu");
        }
        dto.setType(StrUtil.isEmpty(dto.getPath()) ? "1" : "2");
        return menuMapper.addMenu(dto);
    }

    @Override
    public int editMenu(MenuDto dto) {
        if (StrUtil.isEmpty(dto.getIcon())) {
            dto.setIcon("el-icon-menu");
        }
        dto.setType(StrUtil.isEmpty(dto.getPath()) ? "1" : "2");
        return menuMapper.editMenu(dto);
    }
}
