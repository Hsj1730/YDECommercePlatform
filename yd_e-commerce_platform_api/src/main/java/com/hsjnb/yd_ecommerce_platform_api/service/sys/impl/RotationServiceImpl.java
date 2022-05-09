package com.hsjnb.yd_ecommerce_platform_api.service.sys.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.MaterialDto;
import com.hsjnb.yd_ecommerce_platform_api.dto.RotationDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.Rotation;
import com.hsjnb.yd_ecommerce_platform_api.mapper.sys.MaterialMapper;
import com.hsjnb.yd_ecommerce_platform_api.mapper.sys.RotationMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.RotationService;
import com.hsjnb.yd_ecommerce_platform_api.utils.QiNiuYunUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * @date : Created in 2022/05/09 21:13
 * @description :
 */

@Service
public class RotationServiceImpl implements RotationService {

    private final RotationMapper rotationMapper;

    private final QiNiuYunUtil qiNiuYunUtil;

    private final MaterialMapper materialMapper;

    @Autowired
    public RotationServiceImpl(RotationMapper rotationMapper, QiNiuYunUtil qiNiuYunUtil, MaterialMapper materialMapper) {
        this.rotationMapper = rotationMapper;
        this.qiNiuYunUtil = qiNiuYunUtil;
        this.materialMapper = materialMapper;
    }

    @Override
    public PageInfo<Rotation> getRotationList(Map<String, Object> param) {
        PageHelper.startPage(param);
        List<Rotation> rotationList = rotationMapper.getRotationList();
        for (Rotation rotation : rotationList) {
            rotation.setImage(qiNiuYunUtil.getDownloadUrl(rotation.getImage()));
        }
        return new PageInfo<>(rotationList);
    }

    @Override
    public Result deleteRotation(Integer id) {
        rotationMapper.deleteRotation(id);
        return Result.success(null);
    }

    @Override
    public Result setRotationEnable(Integer id, String enable) {
        rotationMapper.setRotationEnable(id,enable);
        return Result.success(null);
    }

    @Override
    public Result saveRotationInfo(RotationDto dto) {
        if (dto.getId() != null) {
            rotationMapper.updateRotation(dto);
        } else {
            rotationMapper.addRotation(dto);
        }
        return Result.success(null);
    }

    @Override
    public RotationDto getRotationInfo(Integer id) {
        RotationDto rotationInfo = rotationMapper.getRotationInfo(id);
        MaterialDto imageByKey;
        imageByKey = materialMapper.getMaterialByKey(rotationInfo.getImage());
        imageByKey.setRealUrl(qiNiuYunUtil.getDownloadUrl(imageByKey.getUrl()));
        MaterialDto[] userImageArr = {imageByKey};
        rotationInfo.setImageArr(userImageArr);
        return rotationInfo;
    }
}
