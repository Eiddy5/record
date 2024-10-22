package org.caffeina.client;

import org.caffeina.CaffeineCache;
import org.caffeina.config.Config;
import org.caffeina.sync.SyncMode;

public class CaffeineClient {
    private int port;
    private SyncMode syncMode;

    public CaffeineClient(int port, SyncMode syncMode) {
        this.port = port;
        this.syncMode = syncMode;
    }

    public static<K,V>  CaffeineCache<K, V> getMap() {
//        Config config = new Config(port, syncMode);
//        return new CaffeineCache<>(config);
        return null;
    }


}


