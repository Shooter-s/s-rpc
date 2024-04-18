package com.shooter.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.shooter.RpcApplication;
import com.shooter.config.RpcConfig;
import com.shooter.constant.RpcConstant;
import com.shooter.loadbalancer.LoadBalancer;
import com.shooter.loadbalancer.LoadBalancerFactory;
import com.shooter.model.RpcRequest;
import com.shooter.model.RpcResponse;
import com.shooter.model.ServiceMetaInfo;
import com.shooter.protocol.*;
import com.shooter.registry.Registry;
import com.shooter.registry.RegistryFactory;
import com.shooter.serializer.JdkSerializer;
import com.shooter.serializer.Serializer;
import com.shooter.serializer.SerializerFactory;
import com.shooter.server.tcp.VertxTcpClient;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.CompletableFuture;

/**
 * ClassName: ServiceProxy
 * Package: com.shooter.proxy
 * Description:
 * @Author:Shooter
 * @Create 2024/4/9 10:42
 * @Version 1.0
 */
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        Serializer serializer = SerializerFactory.getInstance(rpcConfig.getSerializer());

        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(serviceName)
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            // 序列化
            byte[] bodyBytes = serializer.serializer(rpcRequest);
            // 从注册中心获取服务提供者请求地址
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            // 服务发现
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)){
                throw new RuntimeException("暂无服务地址");
            }
            // 从负载均衡器获取一个服务节点
            LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
            // 将调用方法名(请求路径)作为负载均衡参数
            Map<String, Object> requestParams = new HashMap<>();
            requestParams.put("methodName",rpcRequest.getMethodName());
            ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);
            // 发起Tcp请求
            RpcResponse rpcResponse = VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo);
            return rpcResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}