package com.cyber.tank.common.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring工具类 方便在非spring管理环境中获取bean
 */
@Component
/**
 * SpringUtils 的核心定义。
 */
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    /**
     * setApplicationContext 方法。
     * @param context 参数。
     */
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringUtils.applicationContext = context;
    }

    /**
     * getBean 方法。
     * @param clazz 参数。
     * @return 执行结果。
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * getBean 方法。
     * @param name 参数。
     * @return 执行结果。
     */
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }
}
