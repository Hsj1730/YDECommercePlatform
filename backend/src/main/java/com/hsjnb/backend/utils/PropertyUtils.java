package com.hsjnb.backend.utils;

import com.hsjnb.backend.config.PropertyConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
 * @date : Created in 2022/01/15 10:35
 * @description : 配置文件读取工具类
 */

@Slf4j
public class PropertyUtils {

    private static Properties props;

    static {
        loadProps();
    }

    synchronized static private void loadProps() {
        log.info("开始加载properties文件内容.......");
        props = new Properties();
        InputStream in = null;
        PropertyConfig propertyConfig = SpringContextHolder.getBean(PropertyConfig.class);
        try {
            in = PropertyUtils.class.getResourceAsStream("/config-" +
                    propertyConfig.getPropertiesFile() + ".properties");
            props.load(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("config-" + propertyConfig.getPropertiesFile() + ".properties文件未找到");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("出现IOException");
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                log.error("config-" + propertyConfig.getPropertiesFile() + ".properties文件流关闭出现异常");
            }
        }
    }

    public static String getProperty(String key) {
        if (props == null) {
            loadProps();
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if (props == null) {
            loadProps();
        }
        return props.getProperty(key, defaultValue);
    }

}
