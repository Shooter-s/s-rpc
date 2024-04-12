package com.shooter.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * ClassName: MockServiceProxy
 * Package: com.shooter.proxy
 * Description:
 * @Author:Shooter
 * @Create 2024/4/9 19:03
 * @Version 1.0
 */
@Slf4j
public class MockServiceProxy implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 拿到方法调用后的返回值类型，给其生成特定的默认值
        Class<?> methodReturnType = method.getReturnType();
        log.info("mock invoke {}", method.getName());
        return getDefaultObject(methodReturnType);
    }

    private Object getDefaultObject(Class<?> type) {
        // 基本类型
        if (type.isPrimitive()) {
            if (type == boolean.class){
                return false;
            } else if (type == short.class){
                return (short)0;
            }else if (type == int.class){
                return 0;
            }else if (type == long.class){
                return 0L;
            }
        }
        // 引用类型
        return null;
    }
}
