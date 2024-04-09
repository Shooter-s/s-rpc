package com.shooter.provider;

import com.shooter.RpcApplication;
import com.shooter.example.common.service.UserService;
import com.shooter.registry.LocalRegistry;
import com.shooter.server.HttpServer;
import com.shooter.server.VertxHttpServer;

import java.util.Locale;

/**
 * ClassName: ProviderExample
 * Package: com.shooter.provider
 * Description:
 * @Author:Shooter
 * @Create 2024/4/9 17:12
 * @Version 1.0
 */
public class ProviderExample {
    public static void main(String[] args) {
        // 服务注册
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
