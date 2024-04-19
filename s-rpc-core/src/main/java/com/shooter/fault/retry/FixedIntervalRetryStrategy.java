package com.shooter.fault.retry;

import com.github.rholder.retry.*;
import com.shooter.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: FixedIntervalRetryStrategy
 * Package: com.shooter.fault.retry
 * Description:
 * @Author:Shooter
 * @Create 2024/4/19 0:08
 * @Version 1.0
 */
@Slf4j
public class FixedIntervalRetryStrategy implements RetryStrategy{
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        Retryer<RpcResponse> retryer = RetryerBuilder.<RpcResponse>newBuilder()
                .retryIfExceptionOfType(Exception.class) // 遇到报Exception的异常则重试
                .withWaitStrategy(WaitStrategies.fixedWait(3L, TimeUnit.SECONDS)) // 固定3s重试
                .withStopStrategy(StopStrategies.stopAfterAttempt(3)) // 最多重试三次
                .withRetryListener(new RetryListener() { // 每次重试的回调
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        log.info("执行次数{}", attempt.getAttemptNumber());
                    }
                })
                .build();
        RpcResponse rpcResponse = retryer.call(callable);// 给重试对象增添请求任务
        return rpcResponse;
    }
}
