package com.shooter.protocol;

import lombok.Getter;

/**
 * ClassName: ProtocolMessageTypeEnum
 * Package: com.shooter.protocol
 * Description:
 * @Author:Shooter
 * @Create 2024/4/16 23:38
 * @Version 1.0
 */
@Getter
public enum ProtocolMessageTypeEnum {
    REQUEST(0),
    RESPONSE(1),
    HEART_BEAT(2),
    OTHERS(3);

    private final int key;

    ProtocolMessageTypeEnum(int key) {
        this.key = key;
    }

    public static ProtocolMessageTypeEnum getEnumByKey(int key){
        for (ProtocolMessageTypeEnum anEnum : ProtocolMessageTypeEnum.values()) {
            if (anEnum.key == key){
                return anEnum;
            }
        }
        return null;
    }
}
