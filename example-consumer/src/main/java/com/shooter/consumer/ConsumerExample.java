package com.shooter.consumer;

import com.shooter.config.RpcConfig;
import com.shooter.constant.RpcConstant;
import com.shooter.example.common.service.UserService;
import com.shooter.registry.LocalRegistry;
import com.shooter.utils.ConfigUtils;

/**
 * ClassName: ConsumerExample
 * Package: com.shooter.consumer
 * Description:
 * @Author:Shooter
 * @Create 2024/4/9 17:00
 * @Version 1.0
 */
public class ConsumerExample {
    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);
    }
}
