package org.record.hazelcast.client;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.springframework.stereotype.Component;

@Component
public class HazelcastClient {

    private static HazelcastClient hazelcastClient;
    private HazelcastInstance hazelcastInstance;
    private IMap<Object, Object> map;

    private final String MAP_NAME = "test_map";

    public static HazelcastClient Use() {
        if (hazelcastClient == null) {
            hazelcastClient = new HazelcastClient();
        }
        return hazelcastClient;
    }

    private HazelcastClient() {
        Config config = new Config();
        config.setClusterName("dev");
        config.getJetConfig().setEnabled(true).setBackupCount(2);
        NetworkConfig networkConfig = config.getNetworkConfig()
                .setPort(5701)
                .setPortAutoIncrement(true)
                .setPortCount(100);
        JoinConfig join = networkConfig.getJoin();
        join.getTcpIpConfig()
                .setEnabled(true)
                .addMember("localhost:5701")
                .addMember("localhost:5702");

        hazelcastInstance = Hazelcast.newHazelcastInstance(config);
        map = hazelcastInstance.getMap(MAP_NAME);
    }

    public Object get(String key) {
        return map.get(key);
    }

    public Object put(String key, Object value) {
        this.map.set(key, value);
        return value;
    }
}
