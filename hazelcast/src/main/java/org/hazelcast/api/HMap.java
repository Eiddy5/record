package org.hazelcast.api;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public interface HMap {

    Object write(@NotNull String key, @NotNull Object value, long ttl, @NotNull TimeUnit ttlUnit, long maxIdle, @NotNull TimeUnit maxIdleUnit);
}
