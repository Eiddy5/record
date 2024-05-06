package org.record.hazelcast;

import com.hazelcast.map.IMap;
import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HazelMap<T> {
    private T object;
    private IMap<Object, Object> map;

    public HazelMap(T object) {
        this.object = object;
    }

    public List<T> read(String key, Class<T> clazz) {
        return null;
    }

    public T get(String key, Class<T> clazz) {
        return null;
    }

    public boolean write(String key, Class<T> clazz) {
        return false;
    }

    public boolean delete(String key) {
        return false;
    }

    public void put(String key, T value) {
        put(key, value, 3, TimeUnit.DAYS);
    }

    public void put(String key, T value, long ttl) {
        put(key, value, ttl, TimeUnit.DAYS);
    }

    public void put(String key, T value, long ttl, @Nonnull TimeUnit ttlUnit) {
        put(key, value, ttl, ttlUnit, 3, TimeUnit.DAYS);
    }

    public void put(String key, T value, long ttl, @Nonnull TimeUnit ttlUnit, long maxIdle, @Nonnull TimeUnit maxIdleUnit) {
        map.put(key, value, ttl, ttlUnit, maxIdle, maxIdleUnit);
    }

}
