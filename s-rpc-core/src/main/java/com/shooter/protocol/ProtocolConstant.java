package com.shooter.protocol;

/**
 * ClassName: ProtocolConstant
 * Package: com.shooter.protocol
 * Description:
 * @Author:Shooter
 * @Create 2024/4/15 15:29
 * @Version 1.0
 */
public interface ProtocolConstant {

    /**
     * 消息头长度
     */
    int MESSAGE_HEADER_LENGTH = 17;

    /**
     * 协议魔数
     */
    byte PROTOCOL_MAGIC = 0x1;

    /**
     * 协议版本号
     */
    byte PROTOCOL_VERSION = 0x1;

}
