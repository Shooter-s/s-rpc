package com.shooter.consumer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.shooter.example.common.model.User;
import com.shooter.example.common.service.UserService;
import com.shooter.model.RpcRequest;
import com.shooter.model.RpcResponse;
import com.shooter.serializer.JdkSerializer;
import com.shooter.serializer.Serializer;

import java.io.IOException;

/**
 * ClassName: UserServiceProxy
 * Package: com.shooter.proxy
 * Description:
 * @Author:Shooter
 * @Create 2024/4/9 12:47
 * @Version 1.0
 */
public class UserServiceProxy implements UserService {
    @Override
    public User getUser(User user) {
        final Serializer serializer = new JdkSerializer();

        RpcRequest rpcRequest = RpcRequest
                .builder()
                .methodName("getUser")
                .serviceName(UserService.class.getName())
                .parameterTypes(new Class[]{User.class})
                .args(new Object[]{user})
                .build();
        try {
            // 序列化（Java 对象 => 字节数组）
            byte[] bodyBytes = serializer.serializer(rpcRequest);

            // 发送请求
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8080")
                    .body(bodyBytes)
                    .execute()) {
                byte[] result = httpResponse.bodyBytes();
                // 反序列化（字节数组 => Java 对象）
                RpcResponse rpcResponse = serializer.deserializer(result, RpcResponse.class);
                return (User) rpcResponse.getData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
