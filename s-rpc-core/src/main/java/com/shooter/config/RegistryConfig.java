package com.shooter.config;

import com.shooter.registry.RegistryKeys;
import lombok.Data;

/**
 * ClassName: RegistryConfig
 * Package: com.shooter.config
 * Description:
 * @Author:Shooter
 * @Create 2024/4/14 13:37
 * @Version 1.0
 */
@Data
public class RegistryConfig {

    /**
     * 注册中心类别
     */
    private String registry = RegistryKeys.ETCD;

    /**
     * 注册中心地址
     */
    private String address = "http://localhost:2380";

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 超时时间(单位毫秒)
     */
    private Long timeout = 10000L;

}
