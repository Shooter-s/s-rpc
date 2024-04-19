package com.shooter.fault.tolerant;

import com.shooter.model.RpcResponse;

import java.util.Map;

/**
 * ClassName: TolerantStrategy
 * Package: com.shooter.fault.tolerant
 * Description:
 * @Author:Shooter
 * @Create 2024/4/19 14:32
 * @Version 1.0
 */
public interface TolerantStrategy {

    /**
     * 容错
     * @param context 上下文，用于传递数据
     * @param e 异常
     * @return
     */
    RpcResponse doTolerant(Map<String ,Object> context,Exception e);

}
