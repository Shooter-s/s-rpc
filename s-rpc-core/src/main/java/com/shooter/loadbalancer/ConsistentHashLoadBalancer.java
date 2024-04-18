package com.shooter.loadbalancer;

import com.shooter.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * ClassName: ConsistentHashLoadBalancer
 * Package: com.shooter.loadbalancer
 * Description:
 * @Author:Shooter
 * @Create 2024/4/18 18:54
 * @Version 1.0
 */
public class ConsistentHashLoadBalancer implements LoadBalancer {
    /**
     * 一致性Hash环，存放虚拟节点
     * key hash值 ： value 服务元信息
     */
    private final TreeMap<Integer, ServiceMetaInfo> virtualNodes = new TreeMap<>();

    /**
     * 虚拟节点数
     */
    private static final int VIRTUAL_NODE_NUM = 100;

    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        if (serviceMetaInfoList.isEmpty()){
            return null;
        }
        // 构建虚拟节点环
        for (ServiceMetaInfo serviceMetaInfo : serviceMetaInfoList) {
            for (int i = 0; i < VIRTUAL_NODE_NUM; i++) {
                int hash = getHash(serviceMetaInfo.getServiceAddress() + "#" + i);
                virtualNodes.put(hash,serviceMetaInfo);
            }
        }
        // 获取调用请求的值
        int hash = getHash(requestParams);

        // 一致性Hash算法
        Map.Entry<Integer, ServiceMetaInfo> entry = virtualNodes.ceilingEntry(hash);
        if (entry == null){ // 说明没有大于等于调用请求hash值的虚拟节点，返回环首部的节点
            entry = virtualNodes.firstEntry();
        }
        return entry.getValue();
    }

    /**
     * Hash算法，可自行实现
     * @param key
     * @return
     */
    private int getHash(Object key) {
        return key.hashCode();
    }
}
