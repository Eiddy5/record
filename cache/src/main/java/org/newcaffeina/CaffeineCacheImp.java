package org.newcaffeina;

import com.github.benmanes.caffeine.cache.Policy;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import org.checkerframework.checker.index.qual.NonNegative;
import org.jetbrains.annotations.Nullable;
import org.redisson.api.RedissonClient;
import redisson.Redisson;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;

public class CaffeineCacheImp<K,V> implements CaffeineCache<K,V>{
    private RedissonClient client = Redisson.getClient();
    private CaffeineCache<K,V> cache;

    @Nullable
    @Override
    public Object getIfPresent(Object key) {

        return null;
    }

    @Override
    public Object get(Object key, Function mappingFunction) {
        return null;
    }

    @Override
    public Map getAllPresent(Iterable keys) {
        return Map.of();
    }

    @Override
    public void put(Object key, Object value) {

    }

    @Override
    public void putAll(Map map) {

    }

    @Override
    public void invalidate(Object key) {

    }

    @Override
    public void invalidateAll(Iterable keys) {

    }

    @Override
    public void invalidateAll() {

    }

    @Override
    public @NonNegative long estimatedSize() {
        return 0;
    }

    @Override
    public CacheStats stats() {
        return null;
    }

    @Override
    public ConcurrentMap asMap() {
        return null;
    }

    @Override
    public void cleanUp() {

    }

    @Override
    public Policy policy() {
        return null;
    }

    @Override
    public Map getAll(Iterable keys, Function mappingFunction) {
        return Map.of();
    }
}
