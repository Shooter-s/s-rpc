package com.shooter.serializer;


import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * ClassName: Hessian
 * Package: com.shooter.serializer
 * Description:
 * @Author:Shooter
 * @Create 2024/4/10 23:54
 * @Version 1.0
 */
public class HessianSerializer implements Serializer{
    @Override
    public <T> byte[] serializer(T object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HessianOutput ho = new HessianOutput(byteArrayOutputStream);
        ho.writeObject(object);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public <T> T deserializer(byte[] bytes, Class<T> clazz) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        HessianInput hi = new HessianInput(byteArrayInputStream);
        return (T) hi.readObject(clazz);
    }
}
