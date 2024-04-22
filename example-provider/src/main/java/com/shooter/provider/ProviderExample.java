package com.shooter.provider;

import com.shooter.RpcApplication;
import com.shooter.bootstrap.ProviderBootstrap;
import com.shooter.config.RegistryConfig;
import com.shooter.config.RpcConfig;
import com.shooter.example.common.service.UserService;
import com.shooter.model.ServiceMetaInfo;
import com.shooter.model.ServiceRegisterInfo;
import com.shooter.registry.LocalRegistry;
import com.shooter.registry.Registry;
import com.shooter.registry.RegistryFactory;
import com.shooter.server.HttpServer;
import com.shooter.server.VertxHttpServer;
import com.shooter.server.tcp.VertxTcpServer;

import java.util.ArrayList;
import java.util.List;
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
        // 准备要注册的服务
        List<ServiceRegisterInfo<?>> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo<UserService> serviceRegisterInfo = new ServiceRegisterInfo<>(UserService.class.getName(), UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);
        // 服务提供者初始化
        ProviderBootstrap.init(serviceRegisterInfoList);
    }
}
