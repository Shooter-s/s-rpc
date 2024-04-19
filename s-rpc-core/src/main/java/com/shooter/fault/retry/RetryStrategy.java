package com.shooter.fault.retry;

import com.shooter.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * ClassName: RetryStrategy
 * Package: com.shooter.fault.retry
 * Description:
 * @Author:Shooter
 * @Create 2024/4/18 23:45
 * @Version 1.0
 */
public interface RetryStrategy {

    RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception;

}
