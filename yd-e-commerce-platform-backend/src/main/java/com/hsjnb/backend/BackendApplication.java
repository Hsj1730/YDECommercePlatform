package com.hsjnb.backend;

import com.hsjnb.backend.annotation.AnonymousAccess;
import com.hsjnb.backend.util.PropertyUtils;
import com.hsjnb.backend.util.SpringContextHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


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
 * @date : Created in 2022/01/15 1:17
 * @description : 后端应用启动程序
 */

@EnableAsync
@RestController
@SpringBootApplication
@EnableTransactionManagement
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
        System.out.println(
                " ___  ___  ________        ___    _____  ________  ________  ________\n" +
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
        );
    }

    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @GetMapping("/")
    @AnonymousAccess
    public String index() {
        return "Backend service started successfully";
    }

}
