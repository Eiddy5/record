package org.hazelcast;

import com.hazelcast.map.IMap;
import org.hazelcast.client.HazelcastClient;

import java.util.concurrent.TimeUnit;

public class HazelMap<T> {

    private final Class<T> clazz;
    private final IMap<String, Object> dataMap;
    private final IMap<String, Object> schemeMap;

    private HazelMap(Class<T> clazz) {
        this.clazz = clazz;
        this.dataMap = HazelcastClient.DataMap;
        this.schemeMap = HazelcastClient.SchemeMap;
    }


    public HazelMap<T> use(Class<T> clazz) {

        return new HazelMap<>(clazz);
    }


    public Object write(@NotNull String key, @NotNull T value, long ttl, @NotNull TimeUnit ttlUnit, long maxIdle, @NotNull TimeUnit maxIdleUnit) {
        return dataMap.put(key, value, ttl, ttlUnit, maxIdle, maxIdleUnit);
    }
}
