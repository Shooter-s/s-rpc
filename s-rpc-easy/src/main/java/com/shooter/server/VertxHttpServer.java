package com.shooter.server;

import io.vertx.core.Vertx;

/**
 * ClassName: VertxHttpServer
 * Package: com.shooter.server
 * Description:
 * @Author:Shooter
 * @Create 2024/4/7 23:24
 * @Version 1.0
 */
public class VertxHttpServer implements HttpServer{
    @Override
    public void doStart(int port) {
        // 1、创建vertx实例
        Vertx vertx = Vertx.vertx();

        // 2、创建 HTTP 服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        // 3、监听端口并处理请求
        server.requestHandler(new HttpServerHandler());

        // 4、启动HTTP服务器并且监听指定端口
        server.listen(port,result -> {
            if (result.succeeded()){
                System.out.println("Server is now listening on port：" + port);
            }else {
                System.err.println("Failed to start server：" + result.cause());
            }
        });
    }
}
