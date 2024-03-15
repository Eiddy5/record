package org.example.bio.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.http.WebSocket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        int port = 6666;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("服务启动了");
            while (true) {
                System.out.println("等待连接");
                Socket accept = serverSocket.accept();
                System.out.println("连接到一个客户端");
                executorService.execute(() -> {
                    // 和客户端通信
                    handler(accept);
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void handler(Socket socket) {
        try {
            System.out.println("线程名称：" + Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            // 通过socket获取输入流
            System.out.println("read");
            InputStream inputStream = socket.getInputStream();
            int read = inputStream.read(bytes);
            if (read != -1) {
                System.out.println("输出客户端信息" + new String(bytes, 0, read, StandardCharsets.UTF_8));
            }

            OutputStream outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            String res = """
                    HTTP/1.1 200 OK
                    Content-Type:text/html;charset=utf-8
                    hello
                    """;
            out.println(res);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                // 关闭流
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
