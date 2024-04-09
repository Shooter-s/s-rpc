package com.shooter.serializer;

import java.io.IOException;

/**
 * ClassName: Serializer
 * Package: com.shooter.serializer
 * Description:
 * @Author:Shooter
 * @Create 2024/4/9 9:29
 * @Version 1.0
 */
public interface Serializer {

    /**
     * 序列化
     * @param object
     * @return
     * @param <T>
     * @throws IOException
     */
    <T> byte[] serializer(T object) throws IOException;

    /**
     * 反序列化
     * @param bytes
     * @param clazz
     * @return
     * @param <T>
     * @throws IOException
     */
    <T> T deserializer(byte[] bytes,Class<T> clazz) throws IOException;

}
