package com.hsjnb.yd_ecommerce_platform_api.service.sys.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.MaterialDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.Material;
import com.hsjnb.yd_ecommerce_platform_api.mapper.sys.MaterialMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.MaterialService;
import com.hsjnb.yd_ecommerce_platform_api.utils.QiNiuYunUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
 * @date : Created in 2022/03/29 20:13
 * @description :
 */

@Service
public class MaterialServiceImpl implements MaterialService {

    private final MaterialMapper materialMapper;

    private final QiNiuYunUtil qiNiuYunUtil;

    @Autowired
    public MaterialServiceImpl(MaterialMapper materialMapper, QiNiuYunUtil qiNiuYunUtil) {
        this.materialMapper = materialMapper;
        this.qiNiuYunUtil = qiNiuYunUtil;
    }

    @Override
    public PageInfo<MaterialDto> getMaterialList(Map<String, Object> param) {
        PageHelper.startPage(param);
        List<MaterialDto> materialList = materialMapper.getMaterialList((Integer) param.get("groupId"));
        // 获得能够访问得url路径
        for (MaterialDto dto : materialList) {
            dto.setRealUrl(qiNiuYunUtil.getDownloadUrl(dto.getUrl()));
        }
        return new PageInfo<>(materialList);
    }

    @Override
    public Result uploadMaterial(MultipartFile file) {
        String key = qiNiuYunUtil.upload(file);
        return Result.success(key);
    }

    @Override
    public void addMaterial(Material material) {
        if (material.getGroupId() == 0) {
            material.setGroupId(null);
        }
        materialMapper.addMaterial(material);
    }

    @Override
    public void deleteMaterial(MaterialDto dto) {
        materialMapper.deleteMaterial(dto);
        // 删除七牛云数据
        //qiNiuYunUtil.delete(dto.getUrl());
    }
}
