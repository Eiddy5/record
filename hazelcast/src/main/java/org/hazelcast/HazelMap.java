package org.hazelcast;

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
        return new HazelMap<>(clazz, new JacksonCodec<>());
    }

    public <T> boolean write(String key, T value, long ttl, TimeUnit ttlUnit, long maxIdle, TimeUnit maxIdleUnit) {
        try {
            HazelSchema hazelData = new HazelSchema(value);
            byte[] serializedData = codec.serialize(hazelData);
            dataMap.put(key, serializedData);
            // Handle TTL and max idle time as needed
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize data", e);
        }
    }

    public <T> T read(String key, Class<T> type) {
        try {
            byte[] serializedData = dataMap.get(key);
            if (serializedData == null) {
                return null;
            }
            HazelSchema<?> hazelData = codec.deserialize(serializedData);
            Object data = hazelData.getData();
            return type.cast(data);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to deserialize data", e);
        }
    }

    public <T> boolean writeList(String key, List<T> values, long ttl, TimeUnit ttlUnit, long maxIdle, TimeUnit maxIdleUnit) {
        try {
            HazelData<List<T>> hazelData = new HazelData<>(values);
            byte[] serializedData = codec.serialize(hazelData);
            dataMap.put(key, serializedData);
            // Handle TTL and max idle time as needed
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
            HazelData<?> hazelData = codec.deserialize(serializedData);
            Object data = hazelData.getData();
            return (List<T>) data;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to deserialize data", e);
        }
    }


}
