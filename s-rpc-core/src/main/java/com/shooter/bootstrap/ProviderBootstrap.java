package com.shooter.bootstrap;

import com.shooter.RpcApplication;
import com.shooter.config.RegistryConfig;
import com.shooter.config.RpcConfig;
import com.shooter.model.ServiceMetaInfo;
import com.shooter.model.ServiceRegisterInfo;
import com.shooter.registry.LocalRegistry;
import com.shooter.registry.Registry;
import com.shooter.registry.RegistryFactory;
import com.shooter.server.tcp.VertxTcpServer;

import java.util.List;

/**
 * ClassName: ProviderBootstrap
 * Package: com.shooter.bootstrap
 * Description:
 * @Author:Shooter
 * @Create 2024/4/21 16:42
 * @Version 1.0
 */
public class ProviderBootstrap {

    // 初始化
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList){
        // RPC框架初始化
        RpcApplication.init();
        // 读取配置
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        String registryStr = registryConfig.getRegistry();

        // 依次注册服务到注册中心中
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            // 注册服务
            String serviceName = serviceRegisterInfo.getServiceName();
            LocalRegistry.register(serviceName, serviceRegisterInfo.getImplClass());

            // 注册服务到注册中心
            Registry registry = RegistryFactory.getInstance(registryStr);
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        // 启动TCP服务
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }

}
