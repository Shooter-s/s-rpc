package com.shooter.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shooter.model.RpcRequest;
import com.shooter.model.RpcResponse;

import java.io.IOException;

/**
 * ClassName: JsonSerializer
 * Package: com.shooter.serializer
 * Description:
 * @Author:Shooter
 * @Create 2024/4/10 23:08
 * @Version 1.0
 */
public class JsonSerializer implements Serializer {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    // 序列化
    @Override
    public <T> byte[] serializer(T object) throws IOException {
        return OBJECT_MAPPER.writeValueAsBytes(object);
    }

    // 反序列化
    @Override
    public <T> T deserializer(byte[] bytes, Class<T> clazz) throws IOException {
        T obj = OBJECT_MAPPER.readValue(bytes, clazz);
        if (obj instanceof RpcRequest){
            return handleRequest((RpcRequest)obj,clazz);
        }
        if (obj instanceof RpcResponse){
            return handleResponse((RpcResponse)obj,clazz);
        }
        return obj;
    }

    private <T> T handleResponse(RpcResponse rpcResponse, Class<T> clazz) throws IOException {
        // 处理响应数据
        byte[] bytes = OBJECT_MAPPER.writeValueAsBytes(rpcResponse.getData());
        rpcResponse.setData(OBJECT_MAPPER.readValue(bytes,rpcResponse.getDataType()));
        return clazz.cast(rpcResponse);
    }

    /**
     * 由于Object的原始对象会被擦除，导致反序列化时会被作为LinkedHashMap无法转换成原始对象，因此这里做了特殊处理
     * @param rpcRequest
     * @param clazz
     * @return
     * @param <T>
     * @throws IOException
     */
    private <T> T handleRequest(RpcRequest rpcRequest, Class<T> clazz) throws IOException {
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] args = rpcRequest.getArgs();
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            // 如果类型不同，则重新处理一下
            if (!parameterType.isAssignableFrom(args[i].getClass())){
                byte[] bytes = OBJECT_MAPPER.writeValueAsBytes(args[i]);
                args[i] = OBJECT_MAPPER.readValue(bytes, parameterType);
            }
        }
        return clazz.cast(rpcRequest); // cast方法是检查rpcRequest是否是clazz类型或者它的子类，true->rpcRequest，false->报错
    }
}
