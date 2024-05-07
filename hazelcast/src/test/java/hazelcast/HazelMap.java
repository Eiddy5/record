package hazelcast;

import org.hazelcast.client.HazelcastClient;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HazelMap<T> {

    private HazelcastClient<T> hazelcastClient;
    private Class<T> clazz;
    private final Map<String, Object> dataMap;
    private final Map<String, Class<T>> schemeMap;

    private HazelMap(Class<T> clazz) {
        this.clazz = clazz;
        this.dataMap = new HashMap<>();
        this.schemeMap = new HashMap<>();
    }


    public HazelMap<T> use(Class<T> clazz) {
        String name = clazz.getName();
        if (schemeMap.containsKey(name)) {
            this.clazz = schemeMap.get(name);
        }
        schemeMap.put(name, clazz);
        return new HazelMap<>(clazz);
    }


    public Object write(@NotNull String key, @NotNull T value, long ttl, @NotNull TimeUnit ttlUnit, long maxIdle, @NotNull TimeUnit maxIdleUnit) {
        Class<T> tClass = schemeMap.get(clazz.getName());

        return dataMap.put(key, value);
    }
}
