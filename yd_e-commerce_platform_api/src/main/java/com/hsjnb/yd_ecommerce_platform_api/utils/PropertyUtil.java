package com.hsjnb.yd_ecommerce_platform_api.utils;

import com.hsjnb.yd_ecommerce_platform_api.config.PropertyConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * @date : Created in 2022/03/14 13:42
 * @description : 配置文件读取工具类
 */

@Slf4j
public class PropertyUtil {

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
            in = PropertyUtil.class.getResourceAsStream("/config/config-" +
                    propertyConfig.getActive() + ".properties");
            props.load(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("config-" + propertyConfig.getActive() + ".properties文件未找到");
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
                log.error("config-" + propertyConfig.getActive() + ".properties文件流关闭出现异常");
            }
        }
    }

    public static String getProperty(String key) {
        if (props == null) {
            loadProps();
        }
        return existPlaceholder(props.getProperty(key));
    }

    public static String getProperty(String key, String defaultValue) {
        if (props == null) {
            loadProps();
        }
        return existPlaceholder(props.getProperty(key, defaultValue));
    }

    public static String existPlaceholder(String context) {
        List<String> placeholderList = PlaceholderUtil.getPlaceholderList(context);
        if (placeholderList.size() != 0) {
            Map<String, String> placeholderMap = new HashMap<>();
            placeholderList.forEach((e) -> placeholderMap.put(e,props.getProperty(e)));
            context = new StringSubstitutor(placeholderMap).replace(context);
        }
        return context;
    }

}
