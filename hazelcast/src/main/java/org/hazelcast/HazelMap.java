package org.hazelcast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazelcast.query.Predicate;
import io.netty.buffer.ByteBuf;
import org.hazelcast.client.HazelcastClient;
import org.hazelcast.client.codec.JsonCodec;
import org.hazelcast.codec.StringCodec;
import org.hazelcast.metadata.HazelMetadata;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HazelMap<T> {

    private final Class<T> clazz;
    private final Map<String, String> dataMap;
    private final JsonCodec<String> jsonCodec;

    private HazelMap(Class<T> clazz) {
        this.clazz = clazz;
        dataMap = HazelcastClient.KeyValueMap;
        jsonCodec = new StringCodec();
    }

    public static <T> HazelMap<T> use(Class<T> clazz) {
        return new HazelMap<>(clazz);
    }

    public boolean query(Predicate<String, T> predicate) {
        List<T> result = new ArrayList<>();
        return false;
    }

    public T getOne(@NotNull String key) {
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

        return false;
    }

    private boolean writeData(@NotNull String key, HazelMetadata data, long ttl, @NotNull TimeUnit ttlUnit, long maxIdle, @NotNull TimeUnit maxIdleUnit) {
        try {
            ByteBuf byteBuf = jsonCodec.getValueEncoder().encode(data);
            String encode = byteBuf.toString(StandardCharsets.UTF_8);
            if (dataMap.containsKey(key)) {
                dataMap.replace(key, encode);
            } else {
                dataMap.put(key, encode);
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
