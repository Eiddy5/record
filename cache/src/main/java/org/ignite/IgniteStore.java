package org.ignite;

import com.hazelcast.map.IMap;
import org.apache.ignite.IgniteCache;
import org.hazelcast.HazelMap;
import org.hazelcast.client.HazelcastClient;
import org.hazelcast.client.codec.Codec;
import org.hazelcast.client.codec.JacksonCodec;
import org.ignite.client.IgniteClient;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class IgniteStore<T> {
    private IgniteCache<Object, String> dataMap;
    private final Codec<T> codec;
    private final Class<T> clazz;

    private IgniteStore(Class<T> clazz, Codec<T> codec) {
        this.clazz = clazz;
        this.codec = codec;
         dataMap = IgniteClient.getCache("cache");
    }

    public static <T> IgniteStore<T> use(Class<T> clazz) {
        return new IgniteStore<>(clazz, new JacksonCodec<>());
    }
    public boolean containsKey(@NotNull String key) {
        return dataMap.containsKey(key);
    }

    public boolean remove(@NotNull String key) {
        return dataMap.remove(key);
    }


    public boolean write(String key, T value) {
        try {
            String data = codec.serialize(value);
            dataMap.put(key,data);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize data", e);
        }
    }


    public boolean replace(@NotNull String key, T value) {
        try {
            String data = codec.serialize(value);
            return dataMap.replace(key, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean writeList(String key, List<T> values) {
        return writeList(key, values, 7, TimeUnit.DAYS);
    }

    public boolean writeList(String key, List<T> values, int ttl, TimeUnit ttlUnit) {
        return writeList(key, values, ttl, ttlUnit, 7, TimeUnit.DAYS);
    }

    public boolean writeList(@NotNull String key, List<T> values, @NotNull Integer ttl, @NotNull TimeUnit ttlUnit, @NotNull Integer maxIdle, @NotNull TimeUnit maxIdleUnit) {
        try {
            String data = codec.serialize(values);
            dataMap.put(key, data);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize data", e);
        }
    }

    public T read(@NotNull String key) {
        try {
            String data = dataMap.get(key);
            if (data == null) {
                return null;
            }
            return codec.deserialize(data, clazz);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to deserialize data", e);
        }
    }

}
