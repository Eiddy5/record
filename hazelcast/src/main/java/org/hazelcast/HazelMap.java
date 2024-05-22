package org.hazelcast;

import org.hazelcast.client.HazelcastClient;
import org.hazelcast.client.codec.Codec;
import org.hazelcast.codec.JacksonCodec;
import org.hazelcast.schema.HazelSchema;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HazelMap<T> {

    private Map<String, String> dataMap;
    private final Codec<HazelSchema<?>> codec;
    private final Class<T> clazz;

    private HazelMap(Class<T> clazz, Codec<HazelSchema<?>> codec) {
        this.clazz = clazz;
        this.codec = codec;
        dataMap = HazelcastClient.DataMap;
    }

    public static <T> HazelMap<T> use(Class<T> clazz) {
        return new HazelMap<>(clazz, new JacksonCodec<>());
    }

    public boolean containsKey(@NotNull String key) {
        return dataMap.containsKey(key);
    }

    public boolean remove(@NotNull String key) {
        return dataMap.remove(key) != null;
    }


    public boolean write(String key, T value) {
        return write(key, value, 7, TimeUnit.DAYS);
    }

    public boolean write(String key, T value, int ttl, TimeUnit ttlUnit) {
        return write(key, value, ttl, ttlUnit, 3, TimeUnit.DAYS);
    }

    public boolean write(@NotNull String key, T value, @NotNull Integer ttl, @NotNull TimeUnit ttlUnit, @NotNull Integer maxIdle, @NotNull TimeUnit maxIdleUnit) {
        try {
            String data = codec.serialize(HazelSchema.Of(value));
            dataMap.put(key, data, ttl, ttlUnit, maxIdle, maxIdleUnit);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize data", e);
        }
    }

    public boolean replace(@NotNull String key, T value) {
        HazelSchema<T> hazelData = new HazelSchema<>(value);
        try {
            String data = codec.serialize(hazelData);
            return dataMap.replace(key, data) != null;
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
            String data = codec.serialize(HazelSchema.Of(values, true));
            dataMap.put(key, data, ttl, ttlUnit, maxIdle, maxIdleUnit);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize data", e);
        }
    }

    public T read(@NotNull String key) {
        try {
            String bytes = dataMap.get(key);
            if (bytes == null) {
                return null;
            }
            HazelSchema<?> hazelData = codec.deserialize(bytes);
            Object data = hazelData.getData();
            if (hazelData.isList()) throw new RuntimeException("Expected list, but found object");
            return clazz.cast(data);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to deserialize data", e);
        }
    }

    @SuppressWarnings("unchecked")
    public <R> List<R> readList(@NotNull String key) {
        try {
            String bytes = dataMap.get(key);
            if (bytes == null) {
                return null;
            }
            HazelSchema<?> hazelData = codec.deserialize(bytes);
            Object data = hazelData.getData();
            if (!hazelData.isList()) throw new RuntimeException("Expected object, but found list");
            return (List<R>) data;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to deserialize data", e);
        }
    }
}
