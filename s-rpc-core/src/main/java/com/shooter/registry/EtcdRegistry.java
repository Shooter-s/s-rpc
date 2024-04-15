package com.shooter.registry;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.json.JSONUtil;
import com.shooter.config.RegistryConfig;
import com.shooter.model.ServiceMetaInfo;
import io.etcd.jetcd.*;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;
import io.etcd.jetcd.watch.WatchEvent;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
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
     *本机注册的节点 key 集合（用于维护续期）
     */
    private final Set<String > localRegisterNodeKeySet = new HashSet<>();

    /**
     * 注册中心服务缓存(消费端)
     */
    private final RegistryServiceCache registryServiceCache = new RegistryServiceCache();

    /**
     * 正在监听的key的集合(防止重复监听)
     */
    private final Set<String > watchingKeySet = new ConcurrentHashSet<>();

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
        heartBeat(); // 心跳检测，定时任务 10s
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
        localRegisterNodeKeySet.add(registerKey);
    }

    // 服务注销
    @Override
    public void unRegister(ServiceMetaInfo serviceMetaInfo) {
        String registerKey = ETCD_ROOT_PATH + serviceMetaInfo.getServiceNodeKey();
        kvClient.delete(ByteSequence.from(registerKey, StandardCharsets.UTF_8));
        // 从本地缓存移除
        localRegisterNodeKeySet.remove(registerKey);
    }

    // 服务发现，从Etcd获取服务下的节点列表
    @Override
    public List<ServiceMetaInfo> serviceDiscovery(String serviceKey) {
        // 缓存有，走缓存
        List<ServiceMetaInfo> serviceMetaInfos = registryServiceCache.readCache();
        if (serviceMetaInfos != null){
            return serviceMetaInfos;
        }
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
            List<ServiceMetaInfo> serviceMetaInfoList = keyValues.stream().map(keyValue -> {
                String key = keyValue.getKey().toString(StandardCharsets.UTF_8);
                // 监听key的变化
                watch(key);
                String value = keyValue.getValue().toString(StandardCharsets.UTF_8);
                return JSONUtil.toBean(value, ServiceMetaInfo.class);
            }).collect(Collectors.toList());
            // 写入缓存
            registryServiceCache.writeCache(serviceMetaInfoList);
            return serviceMetaInfoList;
        } catch (Exception e) {
            throw new RuntimeException("获取服务列表失败", e);
        }
    }

    @Override
    public void watch(String servieNodeKey) {
        Watch watchClient = client.getWatchClient();
        // 之前该key未被监听，开启监听
        boolean newWatch = watchingKeySet.add(servieNodeKey);
        if (newWatch){
            watchClient.watch(ByteSequence.from(servieNodeKey,StandardCharsets.UTF_8),response -> {
                for (WatchEvent event : response.getEvents()) { // 监听后回调的事件
                    switch (event.getEventType()){
                        // key删除时触发
                        case DELETE:
                            // 清空注册服务缓存
                            registryServiceCache.cleanCache();
                            break;
                        case PUT:
                        default:
                            break;
                    }
                }
            });
        }
    }

    // 注册中心销毁，释放资源
    @Override
    public void destroy() {
        System.out.println("当前节点下线");
        // 下线节点
        for (String key : localRegisterNodeKeySet) {
            try {
                kvClient.delete(ByteSequence.from(key,StandardCharsets.UTF_8)).get();
            } catch (Exception e) {
                throw new RuntimeException(key + "节点下线失败",e);
            }
        }

        if (kvClient != null) {
            kvClient.close();
        }
        if (client != null) {
            client.close();
        }
    }

    @Override
    public void heartBeat() {
        CronUtil.schedule("*/10 * * * * *", new Task() {
            @Override
            public void execute() {
                // 遍历本节点的所有key
                for (String key : localRegisterNodeKeySet) {
                    try {
                        List<KeyValue> keyValues = kvClient.get(ByteSequence.from(key, StandardCharsets.UTF_8))
                                .get()
                                .getKvs();
                        // 该节点已经过期了，需要重启节点才能重新注册
                        if (CollUtil.isEmpty(keyValues)){
                            continue;
                        }
                        // 节点未过期，续期，重新注册
                        KeyValue keyValue = keyValues.get(0);
                        String value = keyValue.getValue().toString(StandardCharsets.UTF_8);
                        ServiceMetaInfo serviceMetaInfo = JSONUtil.toBean(value, ServiceMetaInfo.class);
                        register(serviceMetaInfo);
                    } catch (Exception e) {
                        throw new RuntimeException(key + "续签失败",e);
                    }
                }
            }
        });
        // 支持秒级别的定时任务
        CronUtil.setMatchSecond(true);
        CronUtil.start();
    }
}
