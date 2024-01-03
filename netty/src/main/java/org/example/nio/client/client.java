package org.example.nio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class client {
    public static void main(String[] args) {
        int port = 6666;
        String serverAddr = "localhost";
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(serverAddr, port));
            socketChannel.configureBlocking(false);
            String msg = "hello server";
            socketChannel.write(ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8)));
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = socketChannel.read(buffer);
            if (read > 0) {
                buffer.flip();
                byte[] data = new byte[buffer.remaining()];
                buffer.get(data);
                String resp = new String(data, StandardCharsets.UTF_8);
                System.out.println("server resp: " + resp);
            }
            socketChannel.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
