package com.shooter.config;

import com.shooter.loadbalancer.LoadBalancerKeys;
import com.shooter.serializer.SerializerKeys;
import io.netty.bootstrap.Bootstrap;
import lombok.Data;

/**
 * ClassName: RpcConfig
 * Package: com.shooter.config
 * Description:
 * @Author:Shooter
 * @Create 2024/4/9 15:57
 * @Version 1.0
 */
@Data
public class RpcConfig {

    /**
     * 名称
     */
    private String name = "shooter";

    /**
     * 版本号
     */
    private String version = "1.0";

    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务器端口号
     */
    private Integer serverPort = 7999;

    /**
     * 模拟调用
     */
    private boolean mock = false;

    /**
     * 序列化器
     */
    private String serializer = SerializerKeys.HESSIAN;

    /**
     * 注册中心配置
     */
    private RegistryConfig registryConfig = new RegistryConfig();

    /**
     * 负载均衡器
     */
    private String loadBalancer = LoadBalancerKeys.ROUND_ROBIN;
}
