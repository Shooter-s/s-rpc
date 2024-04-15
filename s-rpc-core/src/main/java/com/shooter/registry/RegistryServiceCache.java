package com.shooter.registry;

import com.shooter.model.ServiceMetaInfo;

import java.util.List;

/**
 * ClassName: RegistryServiceCache
 * Package: com.shooter.registry
 * Description: 注册中心服务本地缓存
 * @Author:Shooter
 * @Create 2024/4/15 12:06
 * @Version 1.0
 */
public class RegistryServiceCache {
    /**
     * 服务缓存
     */
    List<ServiceMetaInfo> serviceCache;

    /**
     * 写缓存
     * @param newServiceCache
     */
    void writeCache(List<ServiceMetaInfo> newServiceCache){
        this.serviceCache = newServiceCache;
    }

    /**
     * 读缓存
     * @return
     */
    List<ServiceMetaInfo> readCache(){
        return serviceCache;
    }

    /**
     * 清空缓存
     */
    void cleanCache(){
        this.serviceCache = null;
    }
}
