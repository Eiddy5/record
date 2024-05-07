package org.hazelcast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.query.Predicate;
import org.hazelcast.client.HazelcastClient;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HazelMap<T> {

    private HazelcastClient<T> hazelcastClient;
    private Class<T> clazz;
    private Map<String, String> dataMap;

    private HazelMap(Class<T> clazz) {
        this.clazz = clazz;
        if (dataMap == null) {
            dataMap = new HashMap<>();
        }
    }

    public static <T> HazelMap<T> use(Class<T> clazz) {
        return new HazelMap<>(clazz);
    }

    public boolean query(Predicate<String, T> predicate) {
        List<T> result = new ArrayList<>();
        return false;
    }

    public T get(@NotNull String key) {
        String s = dataMap.get(key);
        // 获取序列化方式
        ObjectMapper mapper = new ObjectMapper();
        try {
            T t = mapper.readValue(s, clazz);
            return t;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public T write(@NotNull String key, @NotNull T value) {
        return write(key, value, 7, TimeUnit.DAYS, 7, TimeUnit.DAYS);
    }

    public T write(@NotNull String key, @NotNull T value, long ttl, @NotNull TimeUnit ttlUnit, long maxIdle, @NotNull TimeUnit maxIdleUnit) {
        // 获取序列化方式
        ObjectMapper mapper = new ObjectMapper();
        try {
            // 将值序列化
            String s = mapper.writeValueAsString(value);
            // 存放进map中
            dataMap.put(key, s);
            return value;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
}
