package org.hazelcast.api;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface HMap<K, V> {


    boolean isEmpty();


    V get(@NotNull K key);

    V put(@NotNull K key, @NotNull V value);

    V put(@NotNull K key, @NotNull V value, long l, @NotNull TimeUnit timeUnit);

    V put(@Nonnull K key, @Nonnull V value,
          long ttl, @Nonnull TimeUnit ttlUnit,
          long maxIdle, @Nonnull TimeUnit maxIdleUnit);
}
