package org.ignite.map;

import org.apache.ignite.cache.CacheEntry;
import org.apache.ignite.cache.query.*;
import org.apache.ignite.lang.IgniteClosure;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public interface CacheMap<K, V> {
    <R> QueryCursor<R> query(Query<R> qry);

    FieldsQueryCursor<List<?>> query(SqlFieldsQuery qry);

    <T, R> QueryCursor<R> query(Query<T> qry, IgniteClosure<T, R> transformer);

    V get(K key);

    CacheEntry<K, V> getEntry(K key);

    Map<K, V> getAll(Set<? extends K> keys);

    Collection<CacheEntry<K, V>> getEntries(Set<? extends K> keys);

    V getIfAbsent(K k, Function<? super K, ? extends V> logic);

    V getIfPresent(K k, Function<? super K, ? extends V> logic);

    void put(K key, V val);

    void putAll(Map<? extends K, ? extends V> map);

    boolean remove(K key);

    boolean replace(K key, V val);

    void removeAll(Set<? extends K> keys);

    void removeAll();

    void destroy();
}
