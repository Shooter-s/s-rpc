package com.shooter.loadbalancer;

import com.shooter.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * ClassName: LoadBalancer
 * Package: com.shooter.loadbalancer
 * Description:
 * @Author:Shooter
 * @Create 2024/4/18 17:30
 * @Version 1.0
 */
public interface LoadBalancer {

    /**
     * 选择服务调用
     * @param requestParams 请求参数
     * @param serviceMetaInfoList 可用服务列表
     * @return
     */
    ServiceMetaInfo select(Map<String , Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList);

}
