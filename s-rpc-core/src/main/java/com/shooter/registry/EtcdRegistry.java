package com.shooter.registry;

import cn.hutool.json.JSONUtil;
import com.shooter.config.RegistryConfig;
import com.shooter.model.ServiceMetaInfo;
import io.etcd.jetcd.*;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: EtcdRegistry
 * Package: com.shooter.registry
 * Description:
 * @Author:Shooter
 * @Create 2024/4/13 12:12
 * @Version 1.0
 */
public class EtcdRegistry implements Registry {

    private Client client;

    private KV kvClient;

    /**
     * 键存储根路径，为了做区分
     */
    private static final String ETCD_ROOT_PATH = "/rpc/";

    // 初始化方法，读取注册中心配置并初始化客户端对象
    @Override
    public void init(RegistryConfig registryConfig) {
        client = Client.builder()
                .endpoints(registryConfig.getAddress())
                .connectTimeout(Duration.ofMillis(registryConfig.getTimeout()))
                .build();
        kvClient = client.getKVClient();
    }

    // 服务注册，往Etcd存key和value
    @Override
    public void register(ServiceMetaInfo serviceMetaInfo) throws Exception {
        // 创建一个30秒的租约
        Lease leaseClient = client.getLeaseClient();
        long leaseId = leaseClient.grant(30).get().getID();

        String registerKey = ETCD_ROOT_PATH + serviceMetaInfo.getServiceNodeKey();
        // key->value ： 服务提供者地址->服务的元信息
        // key = rpc/serviceName:serviceVersion/serviceAddress  value = serviceMetaInfo
        ByteSequence key = ByteSequence.from(registerKey, StandardCharsets.UTF_8);
        ByteSequence value = ByteSequence.from(JSONUtil.toJsonStr(serviceMetaInfo), StandardCharsets.UTF_8);

        // 将键值与租约关联起来(存key、vlue，带上过期时间)
        PutOption putOption = PutOption.builder().withLeaseId(leaseId).build();
        kvClient.put(key, value, putOption).get();
    }

    // 服务注销
    @Override
    public void unRegister(ServiceMetaInfo serviceMetaInfo) {
        kvClient.delete(ByteSequence.from(ETCD_ROOT_PATH + serviceMetaInfo.getServiceNodeKey(), StandardCharsets.UTF_8));
    }

    // 服务发现，从Etcd获取服务下的节点列表
    @Override
    public List<ServiceMetaInfo> serviceDiscovery(String serviceKey) {
        // 前缀搜索的前缀条件
        String searchPrefix = ETCD_ROOT_PATH + serviceKey + "/";

        // 支持前缀搜索
        GetOption getOption = GetOption.builder().isPrefix(true).build();
        try {
            List<KeyValue> keyValues = kvClient.get(
                    ByteSequence.from(searchPrefix, StandardCharsets.UTF_8),
                    getOption
            ).get().getKvs();
            // 解析key-value
            return keyValues.stream().map(keyValue -> {
                ByteSequence value = keyValue.getValue();
                String valStr = value.toString();
                return JSONUtil.toBean(valStr, ServiceMetaInfo.class);
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("获取服务列表失败", e);
        }
    }

    // 注册中心销毁，释放资源
    @Override
    public void destroy() {
        System.out.println("当前节点下线");
        if (kvClient != null) {
            kvClient.close();
        }
        if (client != null) {
            client.close();
        }
    }
}
