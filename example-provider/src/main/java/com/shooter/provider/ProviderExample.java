package com.shooter.provider;

import com.shooter.RpcApplication;
import com.shooter.config.RegistryConfig;
import com.shooter.config.RpcConfig;
import com.shooter.example.common.service.UserService;
import com.shooter.model.ServiceMetaInfo;
import com.shooter.registry.LocalRegistry;
import com.shooter.registry.Registry;
import com.shooter.registry.RegistryFactory;
import com.shooter.server.HttpServer;
import com.shooter.server.VertxHttpServer;
import com.shooter.server.tcp.VertxTcpServer;

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
        // RPC框架初始化
        RpcApplication.init();

        // 注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 启动web服务
        /*HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());*/

        // 启动TCP服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
