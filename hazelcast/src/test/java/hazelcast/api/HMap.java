package hazelcast.api;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public interface HMap<K, V> {


    boolean isEmpty();


    V get(@NotNull K key);

    V put(@NotNull K key, @NotNull V value);

    V put(@NotNull K key, @NotNull V value, long l, @NotNull TimeUnit timeUnit);

    Object write(@NotNull String key, @NotNull Object value, long ttl, @NotNull TimeUnit ttlUnit, long maxIdle, @NotNull TimeUnit maxIdleUnit);
}
