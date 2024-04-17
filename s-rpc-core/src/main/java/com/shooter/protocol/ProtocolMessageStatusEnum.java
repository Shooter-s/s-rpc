package com.shooter.protocol;

import lombok.Getter;

/**
 * ClassName: ProtocolMessageStatusEnum
 * Package: com.shooter.protocol
 * Description:
 * @Author:Shooter
 * @Create 2024/4/16 23:23
 * @Version 1.0
 */
@Getter
public enum ProtocolMessageStatusEnum {
    OK("ok",20),
    BAD_REQUEST("badRequest",40),
    BAD_RESPONSE("badResponse",50);

    private final String text;

    private final int value;

    ProtocolMessageStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public static ProtocolMessageStatusEnum getEnumByValue(int value) {
        for (ProtocolMessageStatusEnum anEnum : ProtocolMessageStatusEnum.values()) {
            if (anEnum.getValue() == value){
                return anEnum;
            }
        }
        return null;
    }
}
