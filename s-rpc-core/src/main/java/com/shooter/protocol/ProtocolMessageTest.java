package com.shooter.protocol;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import com.shooter.constant.RpcConstant;
import com.shooter.model.RpcRequest;
import io.vertx.core.buffer.Buffer;

import java.io.IOException;

/**
 * ClassName: ProtocolMessageTest
 * Package: com.shooter.protocol
 * Description:
 * @Author:Shooter
 * @Create 2024/4/17 18:49
 * @Version 1.0
 */
public class ProtocolMessageTest {
    
    public static void main(String[] args) throws IOException {
        ProtocolMessage<RpcRequest> protocolMessage = new ProtocolMessage<>();
        // 准备header
        ProtocolMessage.Header header = new ProtocolMessage.Header();
        header.setMagic(ProtocolConstant.PROTOCOL_MAGIC);
        header.setVersion(ProtocolConstant.PROTOCOL_VERSION);
        header.setSerializer((byte) ProtocolMessageSerializerEnum.JDK.getKey());
        header.setType((byte) ProtocolMessageTypeEnum.REQUEST.getKey());
        header.setStatus((byte) ProtocolMessageStatusEnum.OK.getValue()); // 状态
        header.setRequestId(IdUtil.getSnowflakeNextId());
        header.setBodyLength(0); // 置为0也无所谓，因为编码器会根据body长度赋值的
        // 准备body
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setServiceName("myService");
        rpcRequest.setMethodName("myMethod");
        rpcRequest.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
        rpcRequest.setParameterTypes(new Class[]{String.class});
        rpcRequest.setArgs(new Object[]{"aaa","bbb"});
        // 封装ProtocolMessage
        protocolMessage.setHeader(header);
        protocolMessage.setBody(rpcRequest);
        // 先编码，再解码
        Buffer encode = ProtocolMessageEncoder.encode(protocolMessage);
        ProtocolMessage<?> message = ProtocolMessageDecoder.decode(encode);
        System.out.println(message);
        Assert.notNull(message);
    }

}
