package org.ignite.map;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheEntry;
import org.apache.ignite.cache.query.FieldsQueryCursor;
import org.apache.ignite.cache.query.Query;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.lang.IgniteClosure;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class CacheMapImpl<K, V> implements CacheMap<K, V> {
    private final IgniteCache<K, V> cache;

    public CacheMapImpl(IgniteCache<K, V> cache) {
        this.cache = cache;
    }


    @Override
    public <R> QueryCursor<R> query(Query<R> qry) {
        return cache.query(qry);
    }

    @Override
    public FieldsQueryCursor<List<?>> query(SqlFieldsQuery qry) {
        return cache.query(qry);
    }

    @Override
    public <T, R> QueryCursor<R> query(Query<T> qry, IgniteClosure<T, R> transformer) {
        return cache.query(qry, transformer);
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public CacheEntry<K, V> getEntry(K key) {
        return cache.getEntry(key);
    }

    @Override
    public Map<K, V> getAll(Set<? extends K> keys) {
        return cache.getAll(keys);
    }

    @Override
    public Collection<CacheEntry<K, V>> getEntries(Set<? extends K> keys) {
        return cache.getEntries(keys);
    }

    @Override
    public V getIfAbsent(K k, Function<? super K, ? extends V> logic) {
        V val = cache.get(k);
        if (val != null) {
            return val;
        }
        V v = logic.apply(k);
        cache.put(k, v);
        return v;
    }

    @Override
    public V getIfPresent(K k, Function<? super K, ? extends V> logic) {
        V val = cache.get(k);
        if (val == null) {
            return null;
        }
        return logic.apply(k);
    }

    @Override
    public void put(K key, V val) {
        cache.put(key, val);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        cache.putAll(map);
    }

    @Override
    public boolean remove(K key) {
        return cache.remove(key);
    }

    @Override
    public boolean replace(K key, V val) {
        return cache.replace(key, val);
    }

    @Override
    public void removeAll(Set<? extends K> keys) {
        cache.removeAll(keys);
    }

    @Override
    public void removeAll() {
        cache.removeAll();
    }

    @Override
    public void destroy() {
        cache.destroy();
    }
}
