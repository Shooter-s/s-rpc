package com.shooter.loadbalancer;

import com.shooter.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ClassName: RoundRobinLoadBalancer
 * Package: com.shooter.loadbalancer
 * Description:
 * @Author:Shooter
 * @Create 2024/4/18 17:33
 * @Version 1.0
 */
public class RoundRobinLoadBalancer implements LoadBalancer {
    private final AtomicInteger currentIndex = new AtomicInteger(0);

    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {

        if (serviceMetaInfoList.isEmpty()){
            return null;
        }
        // 只有一个服务，无需轮询
        int size = serviceMetaInfoList.size();
        if (size == 1){
            return serviceMetaInfoList.get(0);
        }
        // 轮询返回服务
        int index = currentIndex.getAndIncrement() % size;
        return serviceMetaInfoList.get(index);
    }
}
