package com.hsjnb.yd_ecommerce_platform_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

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
 * @date : Created in 2022/05/10 0:03
 * @description :
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopGoodsDto implements Serializable {

    //商品id
    private Integer id;

    //商品图片
    private String image;

    //商品名称
    private String storeName;
    //商品简介
    private String storeInfo;
    //关键字
    private String keyword;
    //单位名
    private String unitName;
    //分类id
    private Integer cateId;
    //商品价格
    private BigDecimal price;
    //成本价
    private BigDecimal cost;
    //邮费
    private BigDecimal postage;
    //排序
    private Integer sort;
    //销量
    private Integer sales;
    //库存
    private Integer stock;
    //状态（0：未上架，1：上架）
    private String isShow;
    //是否热卖
    private String isHot;
    //是否优惠
    private String isBenefit;
    //是否精品
    private String isBest;
    //是否新品
    private String isNew;
    //是否优品推荐
    private String isGood;

}
