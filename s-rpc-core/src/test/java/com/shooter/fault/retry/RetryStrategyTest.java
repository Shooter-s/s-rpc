package com.shooter.fault.retry;

import com.shooter.model.RpcResponse;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * ClassName: RetryStrategyTest
 * Package: com.shooter.fault.retry
 * Description:
 * @Author:Shooter
 * @Create 2024/4/19 8:03
 * @Version 1.0
 */
public class RetryStrategyTest  {

    final RetryStrategy retryStrategy = new FixedIntervalRetryStrategy();

    @Test
    public void testRetry(){

        try {
            RpcResponse rpcResponse = retryStrategy.doRetry(()->{
                System.out.println("测试重试机制");
                return new RpcResponse();
                //throw new RuntimeException("模拟重试失败");
            });
            System.out.println(rpcResponse);
        } catch (Exception e) {
            System.out.println("重试多次失败");
            e.printStackTrace();
        }

    }

}