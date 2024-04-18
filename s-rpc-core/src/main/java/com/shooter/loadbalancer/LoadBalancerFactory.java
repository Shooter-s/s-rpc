package com.shooter.loadbalancer;

import com.shooter.spi.SpiLoader;

/**
 * ClassName: LoadBalancerFactory
 * Package: com.shooter.loadbalancer
 * Description:
 * @Author:Shooter
 * @Create 2024/4/18 19:28
 * @Version 1.0
 */
public class LoadBalancerFactory {

    static {
        SpiLoader.load(LoadBalancer.class); // 从配置文件读取名称是LoadBalancer的内容，并存入map中
    }

    /**
     * 默认负载均衡器
     */
    private static final LoadBalancer DEFAULT_LOAD_BALANCE = new RoundRobinLoadBalancer();

    /**
     * 获取实例
     */
    public static LoadBalancer getInstance(String key){
        return SpiLoader.getInstance(LoadBalancer.class,key); // 从map中获取key对应负载均衡实现类名称，获取其实例并返回
    }

}
