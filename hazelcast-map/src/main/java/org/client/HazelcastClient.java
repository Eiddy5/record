package org.client;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MulticastConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

public class HazelcastClient {
    private static final String MAP_NAME = "test_map";
    private static IMap<Object, Object> map;
    private static HazelcastInstance instance;
    private static boolean a = false;


    static {
        init();
        map = instance.getMap(MAP_NAME);
    }

    private static void init() {
        if (!a) {
            a = true;
            Config config = new Config();
            config.setClusterName("dev");
            config.getJetConfig()
                    .setEnabled(true)
                    .setBackupCount(1);
            NetworkConfig networkConfig = config.getNetworkConfig()
                    .setPort(5701)
                    .setPortAutoIncrement(true)
                    .setPortCount(10);
            JoinConfig join = networkConfig.getJoin();
            join.setMulticastConfig(new MulticastConfig()
                    .setEnabled(true)
                    .setMulticastGroup("224.3.5.8")
                    .setMulticastPort(54327));
            instance = Hazelcast.newHazelcastInstance(config);
        }
    }

    public static HazelcastInstance getInstance() {
        return instance;
    }
    public IMap<Object,Object> getMap(){
        return map;
    }
}
