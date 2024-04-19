package com.shooter.fault.retry;

import com.shooter.spi.SpiLoader;

/**
 * ClassName: RetryStrategyFactory
 * Package: com.shooter.fault.retry
 * Description:
 * @Author:Shooter
 * @Create 2024/4/19 8:16
 * @Version 1.0
 */
public class RetryStrategyFactory {

    static {
        SpiLoader.load(RetryStrategy.class);
    }
    // 默认重试机制
    private static final RetryStrategy DEFAULT_RETRY_STRATEGY = new NoRetryStrategy();

    // 获取实例
    public static RetryStrategy getInstance(String key){
        return SpiLoader.getInstance(RetryStrategy.class,key);
    }

}
