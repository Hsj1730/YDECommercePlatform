package com.hsjnb.yd_ecommerce_platform_api.service.sys.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.ExpressDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.Express;
import com.hsjnb.yd_ecommerce_platform_api.mapper.sys.ExpressMapper;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.ExpressService;
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
 * @date : Created in 2022/04/03 19:19
 * @description :
 */

@Service
public class ExpressServiceImpl implements ExpressService {

    private final ExpressMapper expressMapper;

    @Autowired
    public ExpressServiceImpl(ExpressMapper expressMapper) {
        this.expressMapper = expressMapper;
    }

    @Override
    public PageInfo<Express> getExpressList(Map<String, Object> param) {
        PageHelper.startPage(param);
        List<Express> expressList = expressMapper.getExpressList();
        return new PageInfo<>(expressList);
    }

    @Override
    public Result saveExpressInfo(ExpressDto dto) {
        if (dto.getId() != null) {
            expressMapper.updateExpress(dto);
        } else {
            expressMapper.addExpress(dto);
        }
        return Result.success(null);
    }

    @Override
    public Result deleteExpress(Integer id) {
        expressMapper.deleteExpress(id);
        return Result.success(null);
    }

    @Override
    public Result setExpressEnable(Integer id, String enable) {
        expressMapper.setExpressEnable(id,enable);
        return Result.success(null);
    }

    @Override
    public Result getExpressInfo(Integer id) {
        return Result.success(expressMapper.getExpressInfo(id));
    }

    @Override
    public Integer checkCode(Integer id, String code) {
        return expressMapper.checkCode(id, code);
    }
}
