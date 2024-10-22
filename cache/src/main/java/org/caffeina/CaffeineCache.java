package org.caffeina;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.caffeina.check.HeartBeat;
import org.caffeina.client.CaffeineClient;
import org.caffeina.config.Config;
import org.caffeina.sync.DataSync;
import org.caffeina.sync.SyncMode;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CaffeineCache<K,V> {
    private final Cache<K, V> localMap = Caffeine.newBuilder().build();
    private final String multicastGroup = "230.0.0.0";
    private final int multicastPort = 5000;
    private final Config config;
    private final HeartBeat heartbeatMonitor = new HeartBeat();
    private final DataSync<K, V> dataSync;


    public CaffeineCache(Config config) {
        this.config = config;
        this.dataSync = new DataSync<>(localMap);

        try {
            MulticastSocket multicastSocket = new MulticastSocket(multicastPort);
            InetAddress multicastAddress = InetAddress.getByName(multicastGroup);
            multicastSocket.joinGroup(multicastAddress);
            startMulticastReceiver(multicastSocket);

            ScheduledExecutorService heartbeatExecutor = Executors.newSingleThreadScheduledExecutor();
            heartbeatExecutor.scheduleAtFixedRate(this::checkHeartbeats, 0, 2000, TimeUnit.MILLISECONDS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startMulticastReceiver(MulticastSocket multicastSocket) {
        Thread receiverThread = new Thread(() -> {
            try {
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                while (true) {
                    multicastSocket.receive(packet);
                    String received = new String(packet.getData(), 0, packet.getLength());
                    if (!received.equals(getNodeIdentifier())) {
                        connectToNode(received);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        receiverThread.start();
    }

    private void connectToNode(String nodeInfo) {
        String[] parts = nodeInfo.split(":");
        String host = parts[0];
        int port = Integer.parseInt(parts[1]);

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connected to node: " + nodeInfo);
            sendLocalMap(socket);
            if (config.getSyncMode() == SyncMode.SYNC) {
                awaitSyncCompletion();
            }
            dataSync.receive(socket);
            heartbeatMonitor.check(nodeInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendLocalMap(Socket socket) throws IOException {
        dataSync.send(socket);
    }

    private void awaitSyncCompletion() {
        while (!allNodesSynced()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean allNodesSynced() {
        return heartbeatMonitor.isAlive(getNodeIdentifier());
    }

    private void checkHeartbeats() {
        heartbeatMonitor.checkAliveNode();
//        for (String nodeInfo : localMap.getNodeIdentifier()) {
//            if (!heartbeatMonitor.isAlive(nodeInfo)) {
//                handleNodeDisconnection(nodeInfo);
//            }
//        }
    }

    private void handleNodeDisconnection(String nodeInfo) {
        System.out.println("Node disconnected: " + nodeInfo);
        heartbeatMonitor.removeNode(nodeInfo);
        localMap.asMap().keySet().removeIf(key -> key.toString().startsWith(nodeInfo));
    }

    private String getNodeIdentifier() {
        return InetAddress.getLoopbackAddress().getHostAddress() + ":" + config.getPort();
    }

    public void put(K key, V value) {
        synchronized (localMap) {
            localMap.put(key, value);
        }
        sendUpdateToPeers(key, value);
    }

    private void sendUpdateToPeers(K key, V value) {
        synchronized (localMap) {
//            for (String node : localMap.asMap().keySet()) {
//                if (!node.equals(getNodeIdentifier())) {
//                    if (config.getSyncMode() == SyncMode.SYNC) {
//                        sendUpdateSync(node, key, value);
//                    } else {
//                        sendUpdateAsync(node, key, value);
//                    }
//                }
//            }
        }
    }

    private void sendUpdateSync(String node, K key, V value) {
        dataSync.send(createSocket(node));
    }

    private void sendUpdateAsync(String node, K key, V value) {
        new Thread(() -> {
            dataSync.send(createSocket(node));
        }).start();
    }

    private Socket createSocket(String node) {
        String[] parts = node.split(":");
        String host = parts[0];
        int port = Integer.parseInt(parts[1]);
        try {
            return new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public V get(K key) {
        synchronized (localMap) {
//            return localMap.get(key);
            return null;
        }
    }

    public void remove(K key) {
        synchronized (localMap) {
//            localMap.remove(key);
        }
        sendUpdateToPeers(key, null);
    }

    public static void main(String[] args) {
        Config config = new Config(5000, SyncMode.SYNC);
        CaffeineCache<String, String> map = new CaffeineCache<>(config);

        // 测试数据存储和同步
        map.put("key1", "value1");
        System.out.println("Node - Get key1: " + map.get("key1"));

        try {
            Thread.sleep(5000); // 等待数据同步完成
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Node - Get key1 after sync: " + map.get("key1")); // 应该输出 "value1"
    }
}


