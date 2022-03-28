package com.hsjnb.yd_ecommerce_platform_api;

import com.hsjnb.yd_ecommerce_platform_api.utils.PropertyUtil;
import com.hsjnb.yd_ecommerce_platform_api.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableAsync
@SpringBootApplication
@EnableTransactionManagement
public class YdECommercePlatformApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(YdECommercePlatformApiApplication.class, args);
        log.info(
                "\n\n ___  ___  ________        ___    _____  ________  ________  ________\n" +
                        "|\\  \\|\\  \\|\\   ____\\      |\\  \\  / __  \\|\\_____  \\|\\_____  \\|\\   __  \\\n" +
                        "\\ \\  \\\\\\  \\ \\  \\___|_     \\ \\  \\|\\/_|\\  \\\\|___/  /\\|____|\\ /\\ \\  \\|\\  \\\n" +
                        " \\ \\   __  \\ \\_____  \\  __ \\ \\  \\|/ \\ \\  \\   /  / /     \\|\\  \\ \\  \\\\\\  \\\n" +
                        "  \\ \\  \\ \\  \\|____|\\  \\|\\  \\\\_\\  \\   \\ \\  \\ /  / /     __\\_\\  \\ \\  \\\\\\  \\\n" +
                        "   \\ \\__\\ \\__\\____\\_\\  \\ \\________\\   \\ \\__Y__/ /     |\\_______\\ \\_______\\\n" +
                        "    \\|__|\\|__|\\_________\\|________|    \\|__|__|/      \\|_______|\\|_______|\n" +
                        "             \\|_________|\n" +
                        "\n亿点电子商务平台服务端启动成功 " +
                        "\n前端访问地址：" + PropertyUtil.getProperty("frontendUrl") +
                        "\nGitHub项目地址：" + PropertyUtil.getProperty("githubUrl") +
                        "\nGitee项目地址：" + PropertyUtil.getProperty("giteeUrl") + "\n"
        );
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

}
