package com.hsjnb.yd_ecommerce_platform_api.controller.sys;

import com.hsjnb.yd_ecommerce_platform_api.annotation.AnonymousAccess;
import com.hsjnb.yd_ecommerce_platform_api.common.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.dto.MaterialDto;
import com.hsjnb.yd_ecommerce_platform_api.entity.Material;
import com.hsjnb.yd_ecommerce_platform_api.service.sys.MaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
 * @date : Created in 2022/03/29 20:11
 * @description :
 */

@Api(tags = "系统管理 - 素材接口")
@RestController
@RequestMapping(value = "material")
public class MaterialController {

    private final MaterialService materialService;

    @Autowired
    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    /**
     * 查询素材列表
     * @param param param
     * @return Result
     */
    @PostMapping(value = "getMaterialList", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "查询素材列表", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result getMaterialList(@RequestBody Map<String, Object> param) {
        return Result.success(materialService.getMaterialList(param));
    }

    /**
     * 素材上传
     * @param file file
     * @return Result
     */
    @PostMapping(value = "uploadMaterial")
    @ApiOperation(value = "素材上传", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result uploadMaterial(MultipartFile file) {
        return Result.success(materialService.uploadMaterial(file));
    }

    /**
     * 添加素材
     * @param material material
     * @return Result
     */
    @AnonymousAccess
    @PostMapping(value = "addMaterial", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "添加素材", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result addMaterial(@RequestBody Material material) {
        materialService.addMaterial(material);
        return Result.success(null);
    }

    /**
     * 删除素材
     * @param dto dto
     * @return Result
     */
    @AnonymousAccess
    @PostMapping(value = "deleteMaterial", produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "删除素材", httpMethod = Constant.HTTP_POST, response = Result.class)
    public Result deleteMaterial(@RequestBody MaterialDto dto) {
        materialService.deleteMaterial(dto);
        return Result.success(null);
    }

}
