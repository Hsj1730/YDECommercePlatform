package com.hsjnb.yd_ecommerce_platform_api.utils;

import com.hsjnb.yd_ecommerce_platform_api.entity.Menu;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
 * @date : Created in 2022/03/21 22:04
 * @description : 树结构工具类
 */

@Component
public class TreeUtil {

    private List<Menu> menuList = new ArrayList<>();

    //建立树形结构
    public List<Menu> buildTree(List<Menu> menus){
        this.menuList = menus;
        List<Menu> treeMenus = new ArrayList<>();
        for(Menu menuNode : getRootNode()) {
            buildChildrenTree(menuNode);
            treeMenus.add(menuNode);
        }
        return treeMenus;
    }

    //递归，建立子树形结构
    private Menu buildChildrenTree(Menu pNode){
        List<Menu> childrenMenus = new ArrayList<>();
        for(Menu menuNode : menuList) {
            if(menuNode.getParentId().equals(pNode.getId())) {
                childrenMenus.add(buildChildrenTree(menuNode));
            }
        }
        pNode.setChildren(childrenMenus);
        return pNode;
    }

    //获取根节点
    private List<Menu> getRootNode() {
        List<Menu> rootMenuLists = new ArrayList<>();
        for (Menu menuNode : menuList) {
            if (menuNode.getParentId() == 0) {
                rootMenuLists.add(menuNode);
            }
        }
        return rootMenuLists;
    }

}
