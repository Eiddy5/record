package org.hazelcast.client;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.sql.SqlService;
import org.x7.json.JsonObject;
import org.x7.paas.registry.MeshDict;

public class HazelcastClient {
    public static final IMap<String, String> DataMap;
    private static final String DATA_MAP = "data_map";
    private static final String ORIGIN_MAP = "origin_map";
    private static boolean a = false;
    private static HazelcastInstance instance;

    static {
        init();
        DataMap = instance.getMap(DATA_MAP);
    }

    public static void start() {
        init();
    }

    protected static void init() {
        if (!a) {
            a = true;
            Config config = new Config();
            JsonObject setting = MeshDict.use().readConfig("hazelcast", "default");
            String clusterName = setting.getString("cluster_name", "okr");
            config.setClusterName(clusterName);
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

    public static <K, V> IMap<K, V> getMap() {
        return instance.getMap(ORIGIN_MAP);
    }

    public static SqlService getSqlService() {
        return instance.getSql();
    }
}
