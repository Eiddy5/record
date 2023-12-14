package org.example.bio.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        int port = 6666;
        String serverAddr = "localhost";

        try (Socket socket = new Socket(serverAddr, port)) {
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            String msg = "hello server";
            outputStream.write(msg.getBytes());
            outputStream.flush();

            byte[] bytes = new byte[1024];
            int read = inputStream.read(bytes);
            String resp = new String(bytes, 0, read);
            System.out.println("server resp: " + resp);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
