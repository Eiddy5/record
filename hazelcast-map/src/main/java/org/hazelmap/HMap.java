package org.hazelmap;

import com.hazelcast.map.IMap;
import org.client.HazelcastClient;

import java.util.concurrent.TimeUnit;

/**
 * @author： Eiddy
 * @description：
 * @project: record
 * @date： 2024/5/1 13:28
 * @version: 1.0
 */
public class HMap<K, V> {
    private IMap<Object, Object> map = HazelcastClient.getMap();


    public void put(String key, Object value) {
        map.put(key, value);
    }

    public Object get(String key) {
        return map.get(key);
    }

    public Object put(K key, V value){
        return put(key,value,3,TimeUnit.DAYS);
    }
    public Object put(K key, V value, long ttl, TimeUnit ttlUnit){
        return put(key,value,ttl,ttlUnit,7,TimeUnit.DAYS);
    }
    public Object put(K key, V value, long ttl, TimeUnit ttlUnit, long maxIdle, TimeUnit maxIdleUnit) {
        Object put = map.put(key, value, ttl, ttlUnit, maxIdle, maxIdleUnit);
        return put;
    }
}
