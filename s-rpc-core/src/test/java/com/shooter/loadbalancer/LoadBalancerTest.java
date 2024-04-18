package com.shooter.loadbalancer;

import com.shooter.model.ServiceMetaInfo;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: LoadBalancerTest
 * Package: com.shooter.loadbalancer
 * Description:
 * @Author:Shooter
 * @Create 2024/4/18 20:07
 * @Version 1.0
 */
public class LoadBalancerTest {

    final LoadBalancer loadBalancer = new RoundRobinLoadBalancer();

    @Test
    public void testLoadBalancerTest(){
        // 请求参数(请求地址)
        Map<String ,Object> requestParams = new HashMap<>();
        requestParams.put("methodName","apple");
        // 服务列表
        ServiceMetaInfo serviceMetaInfo1 = new ServiceMetaInfo();
        serviceMetaInfo1.setServiceName("myService");
        serviceMetaInfo1.setServiceVersion("1.0");
        serviceMetaInfo1.setServiceHost("localhost");
        serviceMetaInfo1.setServicePort(7999);
        ServiceMetaInfo serviceMetaInfo2 = new ServiceMetaInfo();
        serviceMetaInfo2.setServiceName("myService");
        serviceMetaInfo2.setServiceVersion("1.0");
        serviceMetaInfo2.setServiceHost("shooter");
        serviceMetaInfo2.setServicePort(7998);
        List<ServiceMetaInfo> serviceMetaInfoList = Arrays.asList(serviceMetaInfo1, serviceMetaInfo2);
        // 连续调用三次
        ServiceMetaInfo serviceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
        System.out.println(serviceMetaInfo);
        serviceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
        System.out.println(serviceMetaInfo);
        serviceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
        System.out.println(serviceMetaInfo);
    }

}
