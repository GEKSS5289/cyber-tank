package com.cyber.tank.common.core.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

/**
 * Bean工具类
 */
public class BeanUtils {
    /**
     * 对象转对象
     */
    public static <T> T copyProperties(Object source, Class<T> target) {
        return BeanUtil.toBean(source, target);
    }

    /**
     * 忽略空值拷贝
     */
    public static void copyIgnoreNull(Object source, Object target) {
        BeanUtil.copyProperties(source, target, CopyOptions.create().setIgnoreNullValue(true));
    }
}
