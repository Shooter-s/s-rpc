package com.shooter.server.tcp;


import com.shooter.server.HttpServer;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.parsetools.RecordParser;

import java.util.Arrays;

/**
 * ClassName: VertxTcpServer
 * Package: com.shooter.server.tcp
 * Description:
 * @Author:Shooter
 * @Create 2024/4/17 14:03
 * @Version 1.0
 */
public class VertxTcpServer implements HttpServer {

    private byte[] handleRequest(byte[] requestData) {
        // 在这里编写处理请求的逻辑，根据requestData构造响应数据并返回
        // demo
        return "Hello client!".getBytes();
    };

    @Override
    public void doStart(int port) {
        // 创建Vertx实例
        Vertx vertx = Vertx.vertx();

        // 创建TCP服务器
        NetServer server = vertx.createNetServer();

        // 处理请求
        server.connectHandler(new TcpServerHandler());
        /*server.connectHandler(socket -> {
            RecordParser parser = RecordParser.newFixed(8); // 请求头长度
            parser.setOutput(new Handler<Buffer>() {
                // 初始化
                int size = -1;
                Buffer resultBuffer = Buffer.buffer(); // 创建一个buffer实例，用来累积接收到的数据
                @Override
                public void handle(Buffer buffer) {
                    if (-1 == size){
                        size = buffer.getInt(4);
                        parser.fixedSizeMode(size);
                        resultBuffer.appendBuffer(buffer); // 头部加到结果里
                    }else {
                        // 写入体信息到结果里
                        resultBuffer.appendBuffer(buffer);
                        System.out.println(resultBuffer);
                        size = -1;
                        parser.fixedSizeMode(8);
                        resultBuffer = Buffer.buffer();
                    }
                }
            });
            socket.handler(parser);
        });*/


        // 启动TCP服务器并监听指定端口
        server.listen(port,result -> {
            if (result.succeeded()){
                System.out.println("TCP server started on port" + port);
            }else {
                System.out.println("Failed to start TCP server：" +result.cause());
            }
        });
    }
}
