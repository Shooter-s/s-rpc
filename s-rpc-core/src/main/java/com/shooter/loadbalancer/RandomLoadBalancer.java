package com.shooter.loadbalancer;

import com.shooter.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * ClassName: RandomLoadBalancer
 * Package: com.shooter.loadbalancer
 * Description:
 * @Author:Shooter
 * @Create 2024/4/18 17:37
 * @Version 1.0
 */
public class RandomLoadBalancer implements LoadBalancer {
    private final Random random = new Random();
    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        int size = serviceMetaInfoList.size();
        if (size == 0){
            return null;
        }
        if (size == 1){
            return serviceMetaInfoList.get(0);
        }
        // [0,size)
        return serviceMetaInfoList.get(random.nextInt(size));
    }
}
