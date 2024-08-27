# 简易 RPC

什么是 RPC？

RPC允许一个程序(消费者)像调用自己程序的方法一样，调用另一个程序(提供者)的接口，而不需要了解数据的传输处理过程、底层网络通信的细节等。使得开发者可以轻松调用远程服务，快速开放分布式系统。

简易版 RPC 的流程🎁

![image](https://github.com/Shooter-s/s-rpc/blob/master/image/easy-rpc.png)

👀👀👀👀👀

为了使我们框架更加满足开发者的灵活使用，除了使用我们系统提供的序列化器之外，我们框架还支持了 SPI 机制，供开发者自定义序列化器，仅通过在配置文件中配置，然后通过 SpiFactory 根据在全局配置类序列化属性中指定 key 即可获取 Serializer 实例。

例如在 com.shooter.serializer.Serializer 文件做了如下的配置：

```properties
json=com.shooter.serializer.JsonSerializer
kryo=com.shooter.serializer.KryoSerializer
```

然后通过 `final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer())` 动态获取。其中的 getSerializer 可以是 json 或者 kryo

![image](https://github.com/Shooter-s/s-rpc/blob/master/image/serializer-spi.png)
