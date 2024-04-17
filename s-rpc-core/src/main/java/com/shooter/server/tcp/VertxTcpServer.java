package com.shooter.server.tcp;


import com.shooter.server.HttpServer;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;

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

        // 启动TCP服务器并监听指定端口
        server.listen(port,result -> {
            if (result.succeeded()){
                System.out.println("TCP server started on port" + port);
            }else {
                System.out.println("Failed to start TCP server：" +result.cause());
            }
        });
    }

    public static void main(String[] args) {
        new VertxTcpServer().doStart(8888);
    }
}
