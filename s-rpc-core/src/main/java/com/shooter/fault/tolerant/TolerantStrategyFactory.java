package com.shooter.fault.tolerant;

import com.shooter.spi.SpiLoader;

/**
 * ClassName: TolerantStrategyFactory
 * Package: com.shooter.fault.tolerant
 * Description:
 * @Author:Shooter
 * @Create 2024/4/19 15:46
 * @Version 1.0
 */
public class TolerantStrategyFactory {

    static {
        SpiLoader.load(TolerantStrategy.class);
    }

    private static final TolerantStrategy DEFAULT_RETRY_STRATEGY = new FailFastTolerantStrategy();

    public static TolerantStrategy getInstance(String key) {
        return SpiLoader.getInstance(TolerantStrategy.class, key);
    }

}
