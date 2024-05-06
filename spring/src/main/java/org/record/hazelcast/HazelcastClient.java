package org.record.hazelcast;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.apache.catalina.util.StringUtil;
import org.springframework.stereotype.Component;

@Component
public class HazelcastClient {

    private static HazelcastClient hazelcastClient;
    private static HazelcastInstance hazelcastInstance;
    private static final String CLUSTER_NAME = "dev_s";
    private static IMap<Object, Object> map;
    private static final Integer PORT = 5701;

    static {
        init();
        map = hazelcastInstance.getMap("test_map");
    }

    private static void init() {
        Config config = new Config();
        config.getJetConfig()
                .setEnabled(true)
                .setBackupCount(1);
        // 设置网络配置
        NetworkConfig networkConfig = config.getNetworkConfig();
        networkConfig.setPortCount(5701);
        networkConfig.setPortCount(5);
        networkConfig.setPortAutoIncrement(true);
        JoinConfig join = networkConfig.getJoin();
        join.getMulticastConfig().setEnabled(true)
                .setMulticastGroup("224.3.5.8")
                .setMulticastPort(54327);

        // 默认map配置
        MapConfig defaultMap = new MapConfig("default");
        defaultMap.setInMemoryFormat(InMemoryFormat.BINARY)
                .setMaxIdleSeconds(10)
                .setTimeToLiveSeconds(10)
                .setEvictionConfig(
                        new EvictionConfig()
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setMaxSizePolicy(MaxSizePolicy.PER_NODE)
                                .setSize(10)
                );
        config.addMapConfig(defaultMap);
        hazelcastInstance = Hazelcast.newHazelcastInstance(config);
    }

}
