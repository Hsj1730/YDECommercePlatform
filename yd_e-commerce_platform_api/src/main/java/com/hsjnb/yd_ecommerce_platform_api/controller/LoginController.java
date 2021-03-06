package com.hsjnb.yd_ecommerce_platform_api.controller;

import com.google.code.kaptcha.Producer;
import com.hsjnb.yd_ecommerce_platform_api.annotation.AnonymousAccess;
import com.hsjnb.yd_ecommerce_platform_api.common.Constant;
import com.hsjnb.yd_ecommerce_platform_api.common.Result;
import com.hsjnb.yd_ecommerce_platform_api.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
 * @date : Created in 2022/03/16 23:08
 * @description : 登录控制
 */

@Api(tags = "系统管理 - 登录接口")
@RestController
public class LoginController {

    private final RedisUtil redisUtil;

    private final Producer producer;

    @Autowired
    public LoginController(RedisUtil redisUtil, Producer producer) {
        this.redisUtil = redisUtil;
        this.producer = producer;
    }

    /**
     * 获取登录验证码
     * @return Result
     */
    @AnonymousAccess
    @PostMapping(value = "/getCaptcha",produces = Constant.CONTENT_TYPE)
    @ApiOperation(value = "获取验证码",httpMethod = Constant.HTTP_POST,response = Result.class)
    public Result getCaptcha() {
        String code = producer.createText();
        String key = UUID.randomUUID().toString();
        BufferedImage image = producer.createImage(code);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, Constant.IMG_SUFFIX, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        String str = Constant.BASE64_IMAGE_HEADER;
        String captchaImg = str + encoder.encode(outputStream.toByteArray());
        Map<String,String> result = new HashMap<>();
        // 存储到redis中
        redisUtil.set(key,code, 120); // 设置验证码120秒过期
        result.put(Constant.KEY, key);
        result.put(Constant.CAPTCHA_IMG, captchaImg);
        return Result.success(result);
    }
}
