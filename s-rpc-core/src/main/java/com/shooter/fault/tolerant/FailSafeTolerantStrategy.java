package com.shooter.fault.tolerant;

import com.shooter.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * ClassName: FailSafeTolerantStrategy
 * Package: com.shooter.fault.tolerant
 * Description:
 * @Author:Shooter
 * @Create 2024/4/19 15:40
 * @Version 1.0
 */
@Slf4j
public class FailSafeTolerantStrategy implements TolerantStrategy{
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        log.info("静默处理异常", e);
        return new RpcResponse();
    }
}
