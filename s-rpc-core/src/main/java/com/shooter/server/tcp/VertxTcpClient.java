package com.shooter.server.tcp;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetSocket;

/**
 * ClassName: VertxTcpClient
 * Package: com.shooter.server.tcp
 * Description:
 * @Author:Shooter
 * @Create 2024/4/17 14:18
 * @Version 1.0
 */
public class VertxTcpClient {

    public void start(){
        Vertx vertx = Vertx.vertx();
        vertx.createNetClient().connect(8888,"localhost",result->{
            if (result.succeeded()){
                System.out.println("connected to tcp server");
                NetSocket socket = result.result();
                // 发送数据
                socket.write("Hello,server!");
                // 接收响应
                socket.handler(buffer -> {
                    System.out.println("Received response from server："+buffer.toString());
                });
            }else {
                System.out.println("Failed to connect to tcp server");
            }
        });
    }

    public static void main(String[] args) {
        VertxTcpClient vertxTcpClient = new VertxTcpClient();
        vertxTcpClient.start();
    }
}
