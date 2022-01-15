package com.hsjnb.backend.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

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
 * @date : Created in 2022/01/15 10:42
 * @description : 使用SpringContextHolder获取bean实例
 * 以静态变量保存ApplicationContext，可以 在代码任何地方任何时候取出ApplicationContext
 */

@Slf4j
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext = null;

    /**
     * 取得存储在静态变量中的ApplicationContext
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    /**
     * 从静态变量applicationContext中取得bean，自动转型为所赋值对象的类型
     * @param name name
     * @param <T> <T>
     * @return (T) applicationContext.getBean(name)
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量applicationContext中取得bean，自动转型为所赋值对象的类型
     * @param requiredType requiredType
     * @param <T> <T>
     * @return applicationContext.getBean(requiredType)
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    /**
     * 清除SpringContextHolder中的ApplicationContext为Null
     */
    public static void clearHolder() {
        applicationContext = null;
    }

    /**
     * 实现DisposableBean接口，在Context关闭时清理静态变量
     */
    @Override
    public void destroy() {
        SpringContextHolder.clearHolder();
    }

    /**
     * 实现ApplicationContextAware接口，注入Context到静态变量中。
     * @param applicationContext applicationContext
     * @throws BeansException BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContextHolder.applicationContext != null) {
            log.warn("SpringContextHolder中的ApplicationContext被覆盖，原有ApplicationContext为："
                    + SpringContextHolder.applicationContext);
        }
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 检查ApplicationContext不为空。
     */
    private static void assertContextInjected() {
        Validate.validState(applicationContext != null,"applicationContext属性未注入，" +
                "请在applicationContext.xml中定义SpringContextHolder或在SpringBoot启动类中注册SpringContextHolder。");
    }

}
