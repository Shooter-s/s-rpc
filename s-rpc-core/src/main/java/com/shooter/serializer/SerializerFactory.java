package com.shooter.serializer;

import com.shooter.spi.SpiLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName: SerializerFactory
 * Package: com.shooter.serializer
 * Description:
 * @Author:Shooter
 * @Create 2024/4/10 23:59
 * @Version 1.0
 */
public class SerializerFactory {

    static {
        SpiLoader.load(Serializer.class);
    }

    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    /**
     * 获取实例
     */
    public static Serializer getInstance(String key) {
        return SpiLoader.getInstance(Serializer.class,key);
    }

}
