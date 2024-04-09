package com.shooter.proxy;

import java.lang.reflect.Proxy;

/**
 * ClassName: ServiceProxyFactory
 * Package: com.shooter.proxy
 * Description:
 * @Author:Shooter
 * @Create 2024/4/9 10:43
 * @Version 1.0
 */
public class ServiceProxyFactory {

    /**
     * 根据服务类获取代理对象
     *
     * @param serviceClass
     * @param <T>
     * @return
     */
    public static <T> T getProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                new ServiceProxy());
    }

}
