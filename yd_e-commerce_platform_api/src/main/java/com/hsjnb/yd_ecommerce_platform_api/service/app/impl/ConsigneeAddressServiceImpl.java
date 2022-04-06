package com.hsjnb.yd_ecommerce_platform_api.service.app.impl;

import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.ConsigneeAddressDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.ConsigneeAddress;
import com.hsjnb.yd_ecommerce_platform_api.mapper.app.ConsigneeAddressMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.app.ConsigneeAddressService;
import com.hsjnb.yd_ecommerce_platform_api.utils.PhoneUtil;
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
 * @date : Created in 2022/04/04 16:08
 * @description :
 */

@Service
public class ConsigneeAddressServiceImpl implements ConsigneeAddressService {

    private final ConsigneeAddressMapper consigneeAddressMapper;

    @Autowired
    public ConsigneeAddressServiceImpl(ConsigneeAddressMapper consigneeAddressMapper) {
        this.consigneeAddressMapper = consigneeAddressMapper;
    }

    @Override
    public Result add(ConsigneeAddressDto dto) {
        if (!PhoneUtil.isPhoneLegal(dto.getPhone())) {
            return Result.fail("请输入正确的手机号码");
        }
        if (dto.getIsDefault().equals("1")) {
            // 清除原有的默认状态
            consigneeAddressMapper.clearDefault(dto.getUserId());
        }
        consigneeAddressMapper.add(dto);
        return Result.success(null);
    }

    @Override
    public List<ConsigneeAddress> getConsigneeAddressList(Integer userId) {
        return consigneeAddressMapper.getConsigneeAddressList(userId);
    }

    @Override
    public ConsigneeAddressDto getConsigneeAddress(Integer id) {
        return consigneeAddressMapper.getConsigneeAddress(id);
    }

    @Override
    public void setDefault(Integer userId, Integer id) {
        consigneeAddressMapper.clearDefault(userId);
        consigneeAddressMapper.setDefault(id);
    }

    @Override
    public void delete(Integer id) {
        consigneeAddressMapper.delete(id);
    }

    @Override
    public Result edit(ConsigneeAddressDto dto) {
        if (!PhoneUtil.isPhoneLegal(dto.getPhone())) {
            return Result.fail("请输入正确的手机号码");
        }
        if (dto.getIsDefault().equals("1")) {
            // 清除原有的默认状态
            consigneeAddressMapper.clearDefault(dto.getUserId());
        }
        consigneeAddressMapper.edit(dto);
        return Result.success(null);
    }
}
