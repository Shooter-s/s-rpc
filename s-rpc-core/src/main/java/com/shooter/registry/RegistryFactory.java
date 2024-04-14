package com.shooter.registry;

import com.shooter.serializer.JdkSerializer;
import com.shooter.serializer.Serializer;
import com.shooter.spi.SpiLoader;

/**
 * ClassName: SerializerFactory
 * Package: com.shooter.serializer
 * Description:
 * @Author:Shooter
 * @Create 2024/4/10 23:59
 * @Version 1.0
 */
public class RegistryFactory {

    static {
        SpiLoader.load(Registry.class);
    }

    /**
     * 默认序列化器
     */
    private static final Registry DEFAULT_REGISTRY = new EtcdRegistry();

    /**
     * 获取实例
     */
    public static Registry getInstance(String key) {
        return SpiLoader.getInstance(Registry.class,key);
    }

}
