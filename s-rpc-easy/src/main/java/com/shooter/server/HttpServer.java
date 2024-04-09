package com.shooter.server;

/**
 * ClassName: HttpServer
 * Package: com.shooter.server
 * Description: Http服务器接口
 * @Author:Shooter
 * @Create 2024/4/7 23:21
 * @Version 1.0
 */
public interface HttpServer {
    /**
     * 启动服务器
     * @param port
     */
    void doStart(int port);
}
