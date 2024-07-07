package org.caffeina.sync;

public enum SyncMode {

    SYNC("SYNC"),
    ASYNC("ASYNC");

    private String name;
    private SyncMode(String name) {
        this.name = name;
    }

    public SyncMode getSync(String name){
        return switch (name) {
            case "SYNC" -> SYNC;
            case "ASYNC" -> ASYNC;
            default -> null;
        };
    }

}
