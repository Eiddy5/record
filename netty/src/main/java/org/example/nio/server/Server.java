package org.example.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class Server {

    public static void main(String[] args) {
        int port = 6666;
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务在监听" + port + "端口");
            while (true) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        handleAcceptableKey(serverSocketChannel, selector);
                    } else if (key.isReadable()) {
                        handleReadableKey(key);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void handleAcceptableKey(ServerSocketChannel serverSocketChannel, Selector selector) throws IOException {
        SocketChannel accept = serverSocketChannel.accept();
        accept.configureBlocking(false);
        accept.register(selector, SelectionKey.OP_READ);
        System.out.println("接收到来自" + accept.getRemoteAddress() + "的连接");
    }

    public static void handleReadableKey(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int read = socketChannel.read(buffer);
        if (read > 0) {
            buffer.flip();
            byte[] data = new byte[buffer.remaining()];
            buffer.get(data);
            String msg = new String(data);
            System.out.println("接收到的信息是" + msg);
            String resp = "hello client";
            socketChannel.write(ByteBuffer.wrap(resp.getBytes()));
        }

    }
}
