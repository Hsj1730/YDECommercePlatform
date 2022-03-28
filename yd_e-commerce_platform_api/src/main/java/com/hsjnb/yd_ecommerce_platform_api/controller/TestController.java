package com.hsjnb.yd_ecommerce_platform_api.controller;

import com.hsjnb.yd_ecommerce_platform_api.annotation.AnonymousAccess;
import com.hsjnb.yd_ecommerce_platform_api.common.lang.Result;
import com.hsjnb.yd_ecommerce_platform_api.utils.QiNiuYunUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
 * @date : Created in 2022/03/24 0:38
 * @description :
 */

@RestController
public class TestController {

    private final QiNiuYunUtil qiNiuYunUtil;

    @Autowired
    public TestController(QiNiuYunUtil qiNiuYunUtil) {
        this.qiNiuYunUtil = qiNiuYunUtil;
    }

    @PostMapping("upload")
    @AnonymousAccess
    public Result upload(MultipartFile file) {
        return Result.success(qiNiuYunUtil.upload(file));
    }

    @PostMapping("download")
    @AnonymousAccess
    public Result download(String key) {
        return Result.success(qiNiuYunUtil.getDownloadUrl(key));
    }

    @PostMapping("delete")
    @AnonymousAccess
    public Result delete(String key) {
        qiNiuYunUtil.delete(key);
        return Result.success(200,"删除成功",null);
    }
}
