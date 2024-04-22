package com.shooter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: ServiceRegisterInfo
 * Package: com.shooter.model
 * Description:
 * @Author:Shooter
 * @Create 2024/4/21 15:56
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRegisterInfo<T> {

    // 服务全限定名
    private String serviceName;

    // 实现类
    private Class<? extends T> implClass;

}
