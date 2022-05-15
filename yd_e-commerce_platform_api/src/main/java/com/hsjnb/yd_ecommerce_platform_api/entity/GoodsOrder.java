package com.hsjnb.yd_ecommerce_platform_api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
 * @date : Created in 2022/05/15 12:25
 * @description :
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsOrder implements Serializable {

    private Integer id;

    private String orderId;

    private Integer userId;

    private String realName;

    private String phone;

    private String address;

    private Integer cardId;

    private Integer totalNum;

    private BigDecimal totalPrice;

    private BigDecimal totalPostage;

    private BigDecimal payPrice;

    private BigDecimal payPostage;

    private String payStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payTime;

    private String payType;

    private String status;

    private String refundStatus;

    private String refundReasonWapImg;

    private String refundReasonWapExplain;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date refundReasonTime;

    private String refundReasonWap;

    private String refundReason;

    private BigDecimal refundPrice;

    private String deliverySn;

    private String deliveryName;

    private String deliveryType;

    private String deliveryId;

    private String mark;

    private String effective;

    private String remark;

    //添加时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    //修改时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private String isEvaluate;

    private String evaluate;

}
