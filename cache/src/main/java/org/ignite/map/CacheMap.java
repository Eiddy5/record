package org.ignite.map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ignite.IgniteCache;
import org.codec.JsonFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CacheMap<K, V> {
    private final IgniteCache<String, String> cache;
    private final ObjectMapper mapper = JsonFactory.getObjectMapper();

    public CacheMap(IgniteCache<String, String> cache) {
        this.cache = cache;
    }

    public int size() {
        return cache.size();
    }

    public boolean containsKey(K key) {
        String k = JsonFactory.ToJsonContent(key);
        return cache.containsKey(k);
    }

    public V get(K key) {
        String k = JsonFactory.ToJsonContent(key);
        String val = cache.get(k);
        try {
            return mapper.readValue(val, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public V put(K key, V value) {
        return null;
    }

    public V remove(Object key) {
        return null;
    }

    public void putAll(@NotNull Map<? extends K, ? extends V> m) {

    }

    public void clear() {

    }

    public Set<K> keySet() {
        return Set.of();
    }

    public Collection<V> values() {
        return List.of();
    }

}
