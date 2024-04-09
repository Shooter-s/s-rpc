package com.shooter.provider;

import com.shooter.example.common.service.UserService;
import com.shooter.registry.LocalRegistry;
import com.shooter.server.HttpServer;
import com.shooter.server.VertxHttpServer;

/**
 * ClassName: EasyProviderExample
 * Package: com.shooter.provider
 * Description:
 * @Author:Shooter
 * @Create 2024/4/7 23:03
 * @Version 1.0
 */
public class EasyProviderExample {
    public static void main(String[] args) {
        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        HttpServer httpServer = new VertxHttpServer(); // 测试Vert.x的Http
        httpServer.doStart(8080);
    }
}
