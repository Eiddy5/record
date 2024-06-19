package org.ignite.client;


import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheRebalanceMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.cache.PartitionLossPolicy;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.events.EventType;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.ignite.listener.GlobalMemoryListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.cache.expiry.Duration;
import javax.cache.expiry.TouchedExpiryPolicy;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class IgniteClient {
    private static final Logger logger = LoggerFactory.getLogger(IgniteClient.class);
    private static Ignite ignite;
    private static boolean a = false;
    public static final IgniteCache<String, String> StringMap;
    private static final String DATA_MAP = "string_map";

    static {
        init();
        StringMap = createCache(DATA_MAP);
    }

    protected static void init() {
        if (!a) {
            a = true;
            IgniteConfiguration cfg = new IgniteConfiguration();
            cfg.setIgniteInstanceName("ignite");
            cfg.setClientMode(false);

            /*threadPool*/
            cfg.setPublicThreadPoolSize(10);
            cfg.setQueryThreadPoolSize(8);
            cfg.setRebalanceThreadPoolSize(6);
            cfg.setSystemThreadPoolSize(4);
            cfg.setServiceThreadPoolSize(2);
            cfg.setStripedPoolSize(2);
            cfg.setManagementThreadPoolSize(2);
            cfg.setPeerClassLoadingThreadPoolSize(2);
            cfg.setDataStreamerThreadPoolSize(2);
            cfg.setAsyncCallbackPoolSize(2);

            /*discSpi*/
            TcpDiscoverySpi spi = new TcpDiscoverySpi();

            /*ipFinder*/
            TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
            ipFinder.setMulticastGroup("228.1.1.235");
            ipFinder.setAddresses(List.of("193.168.1.245:47500..47505"));
            ipFinder.setMulticastPort(47500);
            spi.setIpFinder(ipFinder);
            cfg.setDiscoverySpi(spi);
            ignite = Ignition.start(cfg);
            ignite.events().localListen(new GlobalMemoryListener(), EventType.EVT_CACHE_OBJECT_PUT, EventType.EVT_CACHE_OBJECT_REMOVED, EventType.EVT_CACHE_OBJECT_EXPIRED);
        }
    }

    protected static <K, V> IgniteCache<K, V> createCache(String cacheName) {
        assert cacheName != null;
        CacheConfiguration<K, V> cacheCfg = new CacheConfiguration<>();
        cacheCfg.setCacheMode(CacheMode.PARTITIONED);
        cacheCfg.setBackups(2);
        cacheCfg.setEagerTtl(true);
        cacheCfg.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC);
        cacheCfg.setRebalanceMode(CacheRebalanceMode.SYNC);
        cacheCfg.setPartitionLossPolicy(PartitionLossPolicy.READ_ONLY_SAFE);
        cacheCfg.setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(new Duration(TimeUnit.DAYS, 7)));
        return createCache(cacheName, cacheCfg);
    }

    protected static <K, V> IgniteCache<K, V> createCache(String cacheName, CacheConfiguration<K, V> cacheCfg) {
        cacheCfg.setName(cacheName);
        return ignite.getOrCreateCache(cacheCfg);
    }

    /*获取原生map*/
    public static <K, V> IgniteCache<K, V> getCache(String cacheName) {
        return createCache(cacheName);
    }

    public static Ignite getIgnite() {
        return ignite;
    }

}
