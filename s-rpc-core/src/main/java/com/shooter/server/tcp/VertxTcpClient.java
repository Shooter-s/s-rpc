package com.shooter.server.tcp;

import cn.hutool.core.util.IdUtil;
import com.shooter.RpcApplication;
import com.shooter.model.RpcRequest;
import com.shooter.model.RpcResponse;
import com.shooter.model.ServiceMetaInfo;
import com.shooter.protocol.*;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * ClassName: VertxTcpClient
 * Package: com.shooter.server.tcp
 * Description:
 * @Author:Shooter
 * @Create 2024/4/17 14:18
 * @Version 1.0
 */
public class VertxTcpClient {

    public static RpcResponse doRequest(RpcRequest rpcRequest, ServiceMetaInfo selectedServiceMetaInfo) throws ExecutionException, InterruptedException {
        // 发送tcp请求
        Vertx vertx = Vertx.vertx();
        NetClient netClient = vertx.createNetClient();
        CompletableFuture<RpcResponse> responseCompletableFuture = new CompletableFuture<>(); // 发起请求后，等待响应变成同步的
        netClient.connect(selectedServiceMetaInfo.getServicePort(), selectedServiceMetaInfo.getServiceHost(),
                result -> { // 失败连接
                    if (!result.succeeded()) {
                        System.err.println("Failed to connect to TCP server");
                        RpcResponse rpcResponse = new RpcResponse();
                        rpcResponse.setException(new RuntimeException("连接失败"));
                        responseCompletableFuture.complete(rpcResponse);
                        return;
                    }
                    // 成功连接
                    System.out.println("Connected to TCP server");
                    NetSocket socket = result.result();
                    // 构建消息
                    ProtocolMessage<RpcRequest> protocolMessage = new ProtocolMessage<>();
                    ProtocolMessage.Header header = new ProtocolMessage.Header();
                    header.setMagic(ProtocolConstant.PROTOCOL_MAGIC);
                    header.setVersion(ProtocolConstant.PROTOCOL_VERSION);
                    header.setSerializer((byte) ProtocolMessageSerializerEnum.getEnumByValue(
                            RpcApplication.getRpcConfig().getSerializer()).getKey());
                    header.setType((byte) ProtocolMessageTypeEnum.REQUEST.getKey());
                    header.setRequestId(IdUtil.getSnowflakeNextId());
                    protocolMessage.setHeader(header);
                    protocolMessage.setBody(rpcRequest);
                    // 发送请求
                    try {
                        Buffer encodeBuffer = ProtocolMessageEncoder.encode(protocolMessage);
                        socket.write(encodeBuffer);
                    } catch (IOException e) {
                        throw new RuntimeException("协议消息编码错误");
                    }

                    // 接受响应，用到了粘包半包装饰者对代码进行了增强
                    TcpBufferHandlerWrapper bufferHandlerWrapper = new TcpBufferHandlerWrapper(buffer -> {
                        try {
                            ProtocolMessage<RpcResponse> rpcResponseProtocolMessage =
                                    (ProtocolMessage<RpcResponse>) ProtocolMessageDecoder.decode(buffer);
                            responseCompletableFuture.complete(rpcResponseProtocolMessage.getBody());
                        } catch (IOException e) {
                            throw new RuntimeException("协议消息解码错误");
                        }
                    });
                    socket.handler(bufferHandlerWrapper);

                });
        RpcResponse rpcResponse = responseCompletableFuture.get();
        if (rpcResponse.getException() != null){
            throw (RuntimeException) rpcResponse.getException();
        }
        netClient.close();
        return rpcResponse;
    }
}
