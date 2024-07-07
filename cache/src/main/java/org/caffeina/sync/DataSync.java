package org.caffeina.sync;

import com.github.benmanes.caffeine.cache.Cache;
import org.caffeina.CaffeineCache;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class DataSync<K,V> {
    private Cache<K,V> localMap;

    public DataSync(Cache<K, V> localMap) {
        this.localMap = localMap;
    }
    public void receive(Socket socket){
        new Thread(()->{
            try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())){
                while (true){
                    Map<K,V> receivedMap = (Map<K, V>) in.readObject();
                    mergeMap(receivedMap);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public void mergeMap(Map<K,V> receivedMap){
        for (Map.Entry<K, V> entry : receivedMap.entrySet()) {
            localMap.put(entry.getKey(), entry.getValue());
        }
    }
    public void send(Socket socket){
        if (null == socket || socket.isClosed()) return;
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            synchronized (localMap) {
                out.writeObject(localMap);
            }
        } catch (IOException e) {

        }
    }
}
