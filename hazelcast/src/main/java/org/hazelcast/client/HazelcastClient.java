package org.hazelcast.client;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class HazelcastClient {
    public static final IMap<String, String> DataMap;
    private static final String KEY_VALUE_MAP_NAME = "keyValue_map";
    private static boolean a = false;
    private static HazelcastInstance instance;

    private HazelcastClient() {
    }

    static {
        init();
        DataMap = instance.getMap(KEY_VALUE_MAP_NAME);
    }

    private static void init() {
        if (!a) {
            a = true;
            Config config = new Config();
            config.setClusterName("dev");
            config.setProperty("hazelcast.logging.type", "log4j");
            config.getJetConfig()
                    .setEnabled(true)
                    .setBackupCount(2);
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
                    .setMaxIdleSeconds(1000 * 60 * 60 * 24 * 7)
                    .setEvictionConfig(
                            new EvictionConfig()
                                    .setEvictionPolicy(EvictionPolicy.LRU)
                                    .setMaxSizePolicy(MaxSizePolicy.PER_NODE)
                    );
            config.addMapConfig(defaultMap);
            instance = Hazelcast.newHazelcastInstance(config);
        }
    }
}
