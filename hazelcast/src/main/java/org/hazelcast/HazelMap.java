package org.hazelcast;

import org.hazelcast.client.HazelcastClient;
import org.hazelcast.client.protobuf.Encoder;
import org.hazelcast.codec.JacksonCodec;
import org.hazelcast.schema.HazelMapSchema;
import org.hazelcast.schema.HazelMetadata;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HazelMap<T> {

    private final Map<String, String> dataMap;
    private final Map<String, HazelMapSchema<T>> schemaMap;
    private final JacksonCodec<T> codec;

    private HazelMap(Class<T> clazz) {
        dataMap = HazelcastClient.KeyValueMap;
        schemaMap = HazelcastClient.SchemeMap;
        codec = new JacksonCodec<>(clazz);
    }

    public static <T> HazelMap<T> use(Class<T> clazz) {
        return new HazelMap<>(clazz);
    }

    public T getOne(@NotNull String key) {
        String s = dataMap.get(key);
        return null;
    }

    public List<T> get(@NotNull String key) {
        return null;
    }

    public T writeOne(@NotNull String key, @NotNull T value, long ttl, @NotNull TimeUnit ttlUnit, long maxIdle, @NotNull TimeUnit maxIdleUnit) {
        return null;
    }

    public boolean write(@NotNull String key, @NotNull List<T> values) {
        return write(key, values, 7, TimeUnit.DAYS, 7, TimeUnit.DAYS);
    }

    public boolean write(@NotNull String key, @NotNull List<T> values, long ttl, @NotNull TimeUnit ttlUnit, long maxIdle, @NotNull TimeUnit maxIdleUnit) {
        HazelMetadata<?> ofed = HazelMetadata.Of();
        return false;
    }

    private boolean writeData(@NotNull String key, HazelMetadata data, long ttl, @NotNull TimeUnit ttlUnit, long maxIdle, @NotNull TimeUnit maxIdleUnit) {
        try {
            Encoder encoder = codec.getValueEncoder();
            String string = encoder.encode(data).toString(StandardCharsets.UTF_8);
            if (dataMap.containsKey(key)) {
                dataMap.replace(key, string);
            } else {
                dataMap.put(key, string);
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
