package com.shooter.fault.retry;

import com.shooter.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * ClassName: NoRetryStrategy
 * Package: com.shooter.fault.retry
 * Description:
 * @Author:Shooter
 * @Create 2024/4/19 0:04
 * @Version 1.0
 */
public class NoRetryStrategy implements RetryStrategy{
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        return callable.call();
    }
}
