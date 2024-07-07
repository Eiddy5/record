package org.ignite.client.cache;

import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.cache.PartitionLossPolicy;
import org.apache.ignite.configuration.CacheConfiguration;

import javax.cache.expiry.Duration;
import javax.cache.expiry.TouchedExpiryPolicy;
import java.util.concurrent.TimeUnit;

public class CacheMapConfig {

    private static final int BACKUPS = 2;

    private static final CacheMode CACHE_MODE = CacheMode.PARTITIONED;

    private static final CacheRebalanceMode BALANCE_MODE = CacheRebalanceMode.SYNC;

    private static final PartitionLossPolicy PARTITION_LOSS_POLICY = PartitionLossPolicy.READ_ONLY_SAFE;

    private static final CacheWriteSynchronizationMode WRITE_SYNC_MODE = CacheWriteSynchronizationMode.FULL_SYNC;

    private static final TimeUnit TIME_UNIT = TimeUnit.DAYS;
    private static final int TTL = 7;

    public static <K, V> CacheConfiguration<K, V> create(String cacheName) {
        CacheConfiguration<K, V> cacheCfg = new CacheConfiguration<>(cacheName);
        cacheCfg.setBackups(BACKUPS);
        cacheCfg.setEagerTtl(true);
        cacheCfg.setCacheMode(CACHE_MODE);
        cacheCfg.setRebalanceMode(BALANCE_MODE);
        cacheCfg.setPartitionLossPolicy(PARTITION_LOSS_POLICY);
        cacheCfg.setWriteSynchronizationMode(WRITE_SYNC_MODE);
        cacheCfg.setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(new Duration(TIME_UNIT, TTL)));
        return cacheCfg;
    }


}
