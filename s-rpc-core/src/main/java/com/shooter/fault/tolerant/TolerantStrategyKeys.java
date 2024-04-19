package com.shooter.fault.tolerant;

/**
 * ClassName: TolerantStrategyKeys
 * Package: com.shooter.fault.tolerant
 * Description:
 * @Author:Shooter
 * @Create 2024/4/19 15:42
 * @Version 1.0
 */
public interface TolerantStrategyKeys {

    /**
     * 静默处理
     */
    String FAIL_SAFE = "failSafe";

    /**
     * 快速失败
     */
    String FAIL_FAST = "failFast";

}
