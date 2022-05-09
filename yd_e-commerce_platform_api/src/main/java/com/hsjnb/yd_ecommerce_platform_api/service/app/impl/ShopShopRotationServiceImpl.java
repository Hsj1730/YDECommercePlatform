package com.hsjnb.yd_ecommerce_platform_api.service.app.impl;

import com.hsjnb.yd_ecommerce_platform_api.entity.Rotation;
import com.hsjnb.yd_ecommerce_platform_api.mapper.app.ShopRotationMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.app.ShopRotationService;
import com.hsjnb.yd_ecommerce_platform_api.utils.PropertyUtil;
import com.hsjnb.yd_ecommerce_platform_api.utils.QiNiuYunUtil;
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
 * @date : Created in 2022/05/09 22:47
 * @description :
 */

@Service
public class ShopShopRotationServiceImpl implements ShopRotationService {

    private final ShopRotationMapper shopRotationMapper;

    private final QiNiuYunUtil qiNiuYunUtil;

    @Autowired
    public ShopShopRotationServiceImpl(ShopRotationMapper shopRotationMapper, QiNiuYunUtil qiNiuYunUtil) {
        this.shopRotationMapper = shopRotationMapper;
        this.qiNiuYunUtil = qiNiuYunUtil;
    }

    @Override
    public List<Rotation> getRotation() {
        List<Rotation> rotationList = shopRotationMapper.getRotation();
        for (Rotation rotation : rotationList) {
            rotation.setImage(qiNiuYunUtil.getDownloadUrl(rotation.getImage()));
            String backendUrl = PropertyUtil.getProperty("backendUrl");
            rotation.setUrl(backendUrl + rotation.getUrl());
        }
        return rotationList;
    }
}
