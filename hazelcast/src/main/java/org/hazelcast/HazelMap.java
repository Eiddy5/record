package org.hazelcast;

import org.hazelcast.client.codec.BaseCodec;
import org.hazelcast.client.codec.Codec;
import org.hazelcast.codec.JacksonCodec;
import org.hazelcast.schema.HazelSchema;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HazelMap<T> {

    private Map<String, byte[]> dataMap;
    private final Codec<HazelSchema> codec;
    private final Class<T> clazz;

    private HazelMap(Class<T> clazz, Codec<HazelSchema> codec) {
        this.clazz = clazz;
        this.codec = codec;
        dataMap = new HashMap<>();
    }

    public static <T> HazelMap<T> use(Class<T> clazz) {
        return new HazelMap<>(clazz, new BaseCodec<>());
    }

    public <T> boolean write(String key, T value, long ttl, TimeUnit ttlUnit, long maxIdle, TimeUnit maxIdleUnit) {
        try {
            HazelSchema<T> hazelData = new HazelSchema<>(value);
            byte[] serializedData = codec.serialize(hazelData);
            dataMap.put(key, serializedData);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize data", e);
        }
    }

    public  T read(String key) {
        try {
            byte[] serializedData = dataMap.get(key);
            if (serializedData == null) {
                return null;
            }
            HazelSchema<?> hazelData = codec.deserialize(serializedData);
            Object data = hazelData.getData();
            return clazz.cast(data);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to deserialize data", e);
        }
    }

    public <T> boolean writeList(String key, List<T> values, long ttl, TimeUnit ttlUnit, long maxIdle, TimeUnit maxIdleUnit) {
        try {
            HazelSchema<List<T>> hazelData = new HazelSchema<>(values);
            byte[] serializedData = codec.serialize(hazelData);
            dataMap.put(key, serializedData);
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize data", e);
        }
    }

    public <T> List<T> readList(String key, Class<T> type) {
        try {
            byte[] serializedData = dataMap.get(key);
            if (serializedData == null) {
                return null;
            }
            HazelSchema<?> hazelData = codec.deserialize(serializedData);
            Object data = hazelData.getData();
            return (List<T>) data;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to deserialize data", e);
        }
    }


}
