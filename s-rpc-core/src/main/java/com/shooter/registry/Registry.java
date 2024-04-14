package com.shooter.registry;

import com.shooter.config.RegistryConfig;
import com.shooter.model.ServiceMetaInfo;

import java.util.List;

/**
 * ClassName: Registry
 * Package: com.shooter.registry
 * Description: 注册中心
 * @Author:Shooter
 * @Create 2024/4/14 13:46
 * @Version 1.0
 */
public interface Registry {
    /**
     * 初始化
     * @param registryConfig
     */
    void init(RegistryConfig registryConfig);

    /**
     * 注册服务
     * @param serviceMetaInfo
     * @throws Exception
     */
    void register(ServiceMetaInfo serviceMetaInfo) throws Exception;

    /**
     * 注销服务
     * @param serviceMetaInfo
     */
    void unRegister(ServiceMetaInfo serviceMetaInfo);

    /**
     * 服务发现(获取某服务下的所有节点，消费端)
     * @param serviceKey
     * @return
     */
    List<ServiceMetaInfo> serviceDiscovery(String serviceKey);

    /**
     * 服务销毁
     */
    void destroy();

}
