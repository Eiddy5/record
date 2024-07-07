package org.caffeina.config;

import org.caffeina.sync.SyncMode;

public class Config {
    private int port;
    private SyncMode syncMode;

    public Config() {
        this.port = 8080;
    }
    public Config(int port, SyncMode syncMode) {
        this.port = port;
        this.syncMode = syncMode;
    }

    public int getPort() {
        return port;
    }

    public SyncMode getSyncMode() {
        return syncMode;
    }

}
