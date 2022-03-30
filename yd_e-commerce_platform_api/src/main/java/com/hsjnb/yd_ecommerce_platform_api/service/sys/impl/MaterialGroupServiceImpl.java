package com.hsjnb.yd_ecommerce_platform_api.service.sys.impl;

import com.hsjnb.yd_ecommerce_platform_api.dto.MaterialGroupDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.MaterialGroup;
import com.hsjnb.yd_ecommerce_platform_api.mapper.sys.MaterialGroupMapper;
import com.hsjnb.yd_ecommerce_platform_api.mapper.sys.MaterialMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.MaterialGroupService;
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
 * @date : Created in 2022/03/29 19:30
 * @description :
 */

@Service
public class MaterialGroupServiceImpl implements MaterialGroupService {

    private final MaterialGroupMapper materialGroupMapper;

    private final MaterialMapper materialMapper;

    @Autowired
    public MaterialGroupServiceImpl(MaterialGroupMapper materialGroupMapper, MaterialMapper materialMapper) {
        this.materialGroupMapper = materialGroupMapper;
        this.materialMapper = materialMapper;
    }

    @Override
    public List<MaterialGroup> getMaterialGroupList() {
        return materialGroupMapper.getMaterialGroupList();
    }

    @Override
    public void addMaterialGroup(String groupName) {
        materialGroupMapper.addMaterialGroup(groupName);
    }

    @Override
    public void updateMaterialGroup(MaterialGroupDto dto) {
        materialGroupMapper.updateMaterialGroup(dto);
    }

    @Override
    public void deleteMaterialGroup(Integer id) {
        // 删除分组
        materialGroupMapper.deleteMaterialGroup(id);
        // 删除分组下得素材
        materialMapper.deleteMaterialByGroupId(id);
    }
}
