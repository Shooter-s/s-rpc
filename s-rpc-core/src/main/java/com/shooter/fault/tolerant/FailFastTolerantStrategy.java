package com.shooter.fault.tolerant;

import com.shooter.model.RpcResponse;

import java.util.Map;

/**
 * ClassName: FailFastTolerantStrategy
 * Package: com.shooter.fault.tolerant
 * Description:
 * @Author:Shooter
 * @Create 2024/4/19 15:37
 * @Version 1.0
 */
public class FailFastTolerantStrategy implements TolerantStrategy {
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务报错，快速失败", e);
    }
}
