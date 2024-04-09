package com.shooter.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.dialect.Props;

/**
 * ClassName: ConfigUtils
 * Package: com.shooter.utils
 * Description:
 * @Author:Shooter
 * @Create 2024/4/9 15:59
 * @Version 1.0
 */
public class ConfigUtils {

    public static <T> T loadConfig(Class<T> clazz, String prefix) {
        return loadConfig(clazz, prefix, "");
    }

    public static <T> T loadConfig(Class<T> clazz, String prefix, String environment) {
        StringBuilder configFileBuilder = new StringBuilder("application");
        if (StrUtil.isNotBlank(environment)) {
            // application-prod
            configFileBuilder.append("-").append(environment);
        }
        // application-prod.properties 或者 application.properties
        configFileBuilder.append(".properties");
        Props props = new Props(configFileBuilder.toString());
        return props.toBean(clazz, prefix);
    }

}
