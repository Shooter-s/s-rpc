package com.shooter.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: ProtocolMessage
 * Package: com.shooter.protocol
 * Description:
 * @Author:Shooter
 * @Create 2024/4/15 15:22
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolMessage<T> {

    /**
     * 消息头
     */
    private Header header;

    /**
     * 消息体(请求或响应对象)
     */
    private T body;

    /**
     * 协议请求头
     */
    @Data
    public static class Header {
        /**
         * 魔术
         */
        private byte magic;

        /**
         * 版本号
         */
        private byte version;

        /**
         * 序列化器
         */
        private byte serializer;

        /**
         * 消息类型(请求/响应)
         */
        private byte type;

        /**
         * 状态
         */
        private byte status;

        /**
         * 请求id
         */
        private long requestId;

        /**
         * 消息体长度
         */
        private int bodyLength;

    }
}
