package com.hsjnb.yd_ecommerce_platform_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @date : Created in 2022/05/15 19:19
 * @description :
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsOrderResultDetailDto implements Serializable {

    private Integer id;
    private String orderId;
    private Integer userId;
    private String realName;
    private String phone;
    private String address;
    private Integer cartId;
    private Integer totalNum;
    private BigDecimal totalPrice;
    private BigDecimal totalPostage;
    private BigDecimal payPrice;
    private BigDecimal payPostage;
    private String payStatus;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String payTime;
    private String payType;
    private String status;
    private String effective;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String updateTime;
    private String isEvaluate;
    private String evaluate;
    private String deliveryAn;
    private String deliveryName;
    private String deliveryType;
    private String deliveryId;
    private String keyword;
    private String storeName;
    private String storeInfo;
    private String sku;
    private String image;
    private String link;
    private Integer goodsId;

}
