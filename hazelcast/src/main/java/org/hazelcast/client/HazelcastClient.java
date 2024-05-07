package org.hazelcast.client;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.util.Map;

public class HazelcastClient<T> {
    public static final IMap<String, Boolean> DiagnosticMap;
    public static final IMap<String, String> KeyValueMap;
    public static final IMap<String, Object> DataMap;
    private static final String SCHEME_MAP_NAME = "scheme_map";
    private static final String DATA_MAP_NAME = "data_map";
    private static final String DIAGNOSTIC_MAP_NAME = "diagnostic_map";
    private static final String KEY_VALUE_MAP_NAME = "keyValue_map";
    private static boolean a = false;
    private static Config config;
    private static HazelcastInstance instance;

    private HazelcastClient() {
    }

    static {
        init();
        DiagnosticMap = instance.getMap(DIAGNOSTIC_MAP_NAME);
        KeyValueMap = instance.getMap(KEY_VALUE_MAP_NAME);
        DataMap = instance.getMap(DATA_MAP_NAME);
    }

    private static void init() {
        if (!a) {
            a = true;
            config = new Config();
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

    public IMap<String, Class<T>> getSchemeMap() {
        return instance.getMap(SCHEME_MAP_NAME);
    }
}
