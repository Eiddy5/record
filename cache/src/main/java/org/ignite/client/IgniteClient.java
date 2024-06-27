package org.ignite.client;


import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteLock;
import org.apache.ignite.Ignition;
import org.apache.ignite.calcite.CalciteQueryEngineConfiguration;
import org.apache.ignite.configuration.BinaryConfiguration;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.configuration.SqlConfiguration;
import org.apache.ignite.events.EventType;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.ignite.client.cache.CacheMapConfig;
import org.ignite.client.cache.QueryEntityFactory;
import org.ignite.client.listener.MemoryChangeListener;
import org.ignite.map.CacheMap;
import org.ignite.map.CacheMapImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class IgniteClient {
    private static final Logger logger = LoggerFactory.getLogger(IgniteClient.class);
    private static Ignite ignite;
    private static boolean a = false;

    static {
        init();
    }

    protected static void init() {
        if (!a) {
            a = true;
            IgniteConfiguration cfg = new IgniteConfiguration();

            cfg.setIgniteInstanceName("dev");
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



            /*sqlEngine*/
            SqlConfiguration sqlCfg = new SqlConfiguration();
            CalciteQueryEngineConfiguration calcQueryCfg = new CalciteQueryEngineConfiguration();
            calcQueryCfg.setDefault(true);
            sqlCfg.setQueryEnginesConfiguration(calcQueryCfg);

            /*discSpi*/
            TcpDiscoverySpi spi = new TcpDiscoverySpi();

            /*ipFinder*/
            TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
            ipFinder.setMulticastGroup("228.1.1.235");
//            ipFinder.setAddresses(List.of("193.168.1.245:47500..47505"));
            ipFinder.setMulticastPort(47500);
            spi.setIpFinder(ipFinder);
            cfg.setDiscoverySpi(spi);


            /*eventType*/
            cfg.setIncludeEventTypes(EventType.EVT_CACHE_OBJECT_PUT, EventType.EVT_CACHE_OBJECT_REMOVED, EventType.EVT_CACHE_OBJECT_EXPIRED);
            ignite = Ignition.start(cfg);
            ignite.events().localListen(new MemoryChangeListener(), EventType.EVT_CACHE_OBJECT_PUT, EventType.EVT_CACHE_OBJECT_REMOVED, EventType.EVT_CACHE_OBJECT_EXPIRED);
        }
    }

    protected static <K, V> CacheMap<K, V> createCache(String cacheName) {
        assert cacheName != null;
        return createCache(CacheMapConfig.create(cacheName));
    }


    protected static <K, V> CacheMap<K, V> createCache(CacheConfiguration<K, V> cacheCfg) {
        IgniteCache<K, V> cache = ignite.getOrCreateCache(cacheCfg);
        return new CacheMapImpl<>(cache);
    }

    /**
     * 创建支持query的缓存
     *
     * @param cacheName 缓存map名称
     * @param keyType   缓存map key类型
     * @param valueType 缓存value对象
     * @param <K>
     * @param <V>
     * @return
     */
    protected static <K, V> CacheMap<K, V> crateQueryCache(String cacheName, Class<?> keyType, Class<?> valueType) {
        CacheConfiguration<K, V> queryCacheCfg = CacheMapConfig.create(cacheName);
        queryCacheCfg.setQueryEntities(List.of(QueryEntityFactory.create(keyType, valueType)));
        return new CacheMapImpl<>(ignite.getOrCreateCache(queryCacheCfg));
    }

    public static <K, V> CacheMap<K, V> getCache(String cacheName) {
        return createCache(cacheName);
    }

    public static <K, V> CacheMap<K, V> getQueryCache(String cacheName, Class<?> keyType, Class<?> valueType) {
        return crateQueryCache(cacheName, keyType, valueType);
    }

    /*默认获取公平锁*/
    public static IgniteLock getLock(String lockName) {
        return getLock(lockName, true);
    }

    public static IgniteLock getLock(String lockName, boolean fair) {
        return createLock(lockName, true, fair, true);
    }

    protected static IgniteLock createLock(String lockName, boolean failoverSafe, boolean fair, boolean create) {
        return ignite.reentrantLock(lockName, failoverSafe, fair, create);
    }

    public static Ignite getIgnite() {
        return ignite;
    }

}
