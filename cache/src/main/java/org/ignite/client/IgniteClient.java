package org.ignite.client;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;

import java.util.Collections;

public class IgniteClient {
    private IgniteClient igniteClient;
    private static Ignite ignite;
    private static boolean a = false;




    static {
        init();
    }

    private static void init(){
        if (!a){
            a = true;
            IgniteConfiguration cfg = new IgniteConfiguration();
            cfg.setPeerClassLoadingEnabled(true);
            // 多播
            TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
            ipFinder.setMulticastPort(47500);
            ipFinder.setAddresses(Collections.singletonList("228.10.10.45:47500..47505"));
//            ipFinder.setMulticastGroup("228.10.10.45");
            ipFinder.setTimeToLive(200);
            cfg.setDiscoverySpi(new TcpDiscoverySpi().setIpFinder(ipFinder));
            ignite = Ignition.start(cfg);
        }
    }
    public static  <K,V> IgniteCache<K,V> getCache(String name){
        return ignite.getOrCreateCache(name);
    }
}
