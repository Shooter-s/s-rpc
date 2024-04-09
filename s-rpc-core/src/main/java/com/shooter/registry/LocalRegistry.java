package com.shooter.registry;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ClassName: LocalRegistry
 * Package: com.shooter.register
 * Description: 本地注册中心
 * @Author:Shooter
 * @Create 2024/4/7 23:46
 * @Version 1.0
 */
public class LocalRegistry {

    private static final Map<String, Class<?>> MAP = new ConcurrentHashMap<>();

    /**
     * 注册服务
     * @param serviceName
     * @param clazz
     */
    public static void register(String serviceName, Class<?> clazz) {
        MAP.put(serviceName,clazz);
    }

    /**
     * 获取服务
     * @param serviceName
     * @return
     */
    public static Class<?> get(String serviceName) {
        return MAP.get(serviceName);
    }

    /**
     * 删除服务
     * @param serviceName
     */
    public static void remove(String serviceName){
        MAP.remove(serviceName);
    }

}
