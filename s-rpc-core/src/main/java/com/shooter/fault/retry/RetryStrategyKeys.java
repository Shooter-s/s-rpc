package com.shooter.fault.retry;

/**
 * ClassName: RetryStrategyKeys
 * Package: com.shooter.fault.retry
 * Description:
 * @Author:Shooter
 * @Create 2024/4/19 8:13
 * @Version 1.0
 */
public interface RetryStrategyKeys {

    /**
     * 不重试
     */
    String NO = "no";

    /**
     * 固定时间间隔
     */
    String FIXED_INTERVAL = "fixedInterval";

}
