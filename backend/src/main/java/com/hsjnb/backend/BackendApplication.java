package com.hsjnb.backend;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hsjnb.backend.annotation.AnonymousAccess;
import com.hsjnb.backend.utils.PropertyUtils;
import com.hsjnb.backend.utils.ResponseDataUtils;
import com.hsjnb.backend.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@EnableAsync
@Controller
@SpringBootApplication
@EnableTransactionManagement
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
        log.info(
                "\n\n ___  ___  ________        ___    _____  ________  ________  ________\n" +
                        "|\\  \\|\\  \\|\\   ____\\      |\\  \\  / __  \\|\\_____  \\|\\_____  \\|\\   __  \\\n" +
                        "\\ \\  \\\\\\  \\ \\  \\___|_     \\ \\  \\|\\/_|\\  \\\\|___/  /\\|____|\\ /\\ \\  \\|\\  \\\n" +
                        " \\ \\   __  \\ \\_____  \\  __ \\ \\  \\|/ \\ \\  \\   /  / /     \\|\\  \\ \\  \\\\\\  \\\n" +
                        "  \\ \\  \\ \\  \\|____|\\  \\|\\  \\\\_\\  \\   \\ \\  \\ /  / /     __\\_\\  \\ \\  \\\\\\  \\\n" +
                        "   \\ \\__\\ \\__\\____\\_\\  \\ \\________\\   \\ \\__Y__/ /     |\\_______\\ \\_______\\\n" +
                        "    \\|__|\\|__|\\_________\\|________|    \\|__|__|/      \\|_______|\\|_______|\n" +
                        "             \\|_________|\n" +
                        "\n亿点电子商务平台服务端启动成功 \n前端访问地址："
                        + PropertyUtils.getProperty("frontendProtocol") + "://"
                        + PropertyUtils.getProperty("frontendIp") + ":"
                        + PropertyUtils.getProperty("frontendPort")
                        + PropertyUtils.getProperty("frontendPath")
                        + "\nGitHub项目地址：" + PropertyUtils.getProperty("githubUrl")
                        + "\nGitee项目地址：" + PropertyUtils.getProperty("giteeUrl") + "\n\n"
        );
    }

    @Bean
    @Lazy(false)
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @GetMapping("/")
    @AnonymousAccess
    public String index() {
        return "index";
    }

    @GetMapping("/refreshJump")
    @AnonymousAccess
    @ResponseBody
    public JSONObject refreshJump() {
        String url = PropertyUtils.getProperty("frontendProtocol") + "://"
                + PropertyUtils.getProperty("frontendIp") + ":"
                + PropertyUtils.getProperty("frontendPort")
                + PropertyUtils.getProperty("frontendPath");
        Map<String, String> map = new HashMap<>();
        map.put("url",url);
        ResponseDataUtils<Map<String, String>> success = ResponseDataUtils.success(map);
        return JSONObject.parseObject(JSON.toJSONString(success));
    }

}

