package org.hazelcast;

import com.hazelcast.map.IMap;
import com.hazelcast.sql.SqlResult;
import com.hazelcast.sql.SqlRow;
import com.hazelcast.sql.SqlService;
import org.jetbrains.annotations.NotNull;
import org.okr.server.hazelcast.client.HazelcastClient;
import org.okr.server.hazelcast.client.codec.Codec;
import org.okr.server.hazelcast.codec.JsonCodec;
import org.x7.util.text.StringUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HazelStore<T> {

    private static final IMap<String, String> dataMap = HazelcastClient.DataMap;
    private final Codec codec;
    private final Class<T> clazz;


    public HazelStore(Class<T> clazz, JsonCodec codec) {
        this.codec = codec;
        this.clazz = clazz;
    }

    public static <T> HazelStore<T> use(Class<T> clazz) {
        return new HazelStore<>(clazz, new JsonCodec());
    }

    public static boolean containsKey(@NotNull String key) {
        return dataMap.containsKey(key);
    }

    public static boolean remove(@NotNull String key) {
        return dataMap.remove(key) != null;
    }

    public static void clear() {
        dataMap.clear();
    }

    public boolean write(String key, Object value) {
        return write(key, value, 7, TimeUnit.DAYS);
    }

    public boolean write(@NotNull String key, Object value, @NotNull Integer maxIdle, @NotNull TimeUnit maxIdleUnit) {
        String data = codec.serialize(value);
        dataMap.put(key, data, 30, TimeUnit.DAYS, maxIdle, maxIdleUnit);
        return true;
    }

    public boolean replace(@NotNull String key, T value) {
        String data = codec.serialize(value);
        return dataMap.replace(key, data) != null;
    }

    @SuppressWarnings("unchecked")
    public T read(@NotNull String key) {
        String data = dataMap.get(key);
        if (StringUtil.isEmpty(data)) {
            return null;
        }
        checkClazz();
        return (T) codec.deserialize(data, clazz);
    }

    public List<T> readList(@NotNull String key) {
        return readComplex(key);
    }

    public <K, V> Map<K, V> readMap(@NotNull String key) {
        return readComplex(key);
    }

    @SuppressWarnings("unchecked")
    public <R> R readComplex(@NotNull String key) {
        String data = dataMap.get(key);
        if (StringUtil.isEmpty(data)) {
            return null;
        }
        Object deserialize = codec.deserialize(data, Object.class);
        return (R) deserialize;
    }

    private void checkClazz() {
        if (clazz == null) {
            throw new RuntimeException("clazz is null");
        }
    }

    public List<Object> get(String sql, Object... params) {
        ArrayList<Object> list = new ArrayList<>();
        SqlService sqlService = HazelcastClient.getSqlService();
        try (SqlResult result = sqlService.execute(sql, params)) {
            Iterator<SqlRow> iterator = result.stream().iterator();
            if (iterator.hasNext()) {
                list.add(iterator.next());
            }
            return list;
        }
    }

}
