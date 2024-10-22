package org.caffeina.check;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class HeartBeat {
    private static final ConcurrentMap<String ,Long> lastHeartBeat = new ConcurrentHashMap<>();

    public void removeNode(String nodeInfo){
        lastHeartBeat.remove(nodeInfo);
    }

    public void check(String nodeInfo){
        lastHeartBeat.put(nodeInfo,System.currentTimeMillis());
    }
    public boolean isAlive(String nodeInfo){
        Long lastHeartBeatTime = lastHeartBeat.get(nodeInfo);
        if(lastHeartBeatTime == null){
            return false;
        }
        return System.currentTimeMillis() - lastHeartBeatTime < 10000;
    }
    public void checkAliveNode(){
        long now = System.currentTimeMillis();
        lastHeartBeat.entrySet().removeIf(entry -> now - entry.getValue() > 30000);
    }
}
