package com.hsjnb.yd_ecommerce_platform_api.entity;

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
 * @date : Created in 2022/03/29 16:11
 * @description : 树节点父类，所有需要使用TreeUtil工具类形成树形结构等操作的节点都需要实现该接口
 */

public interface TreeNode<T> {

    /**
     * 获取节点id
     * @return 树节点id
     */
    T id();

    /**
     * 获取该节点的父节点id
     * @return 父节点id
     */
    T parentId();

    /**
     * 是否是根节点
     * @return true：根节点
     */
    boolean root();

    /**
     * 设置节点的子节点列表
     * @param children children 子节点
     */
    void setChildren(List<? extends TreeNode<T>> children);

    /**
     * 获取所有子节点
     * @return 子节点列表
     */
    List<? extends TreeNode<T>> getChildren();

}
