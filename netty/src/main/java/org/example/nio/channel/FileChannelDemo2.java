package org.example.nio.channel;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo2 {
    public static void main(String[] args) throws IOException {

        File file = new File("/workspace/test/test.txt");
        FileInputStream inputStream = new FileInputStream(file);
        FileChannel channel = inputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
        channel.read(buffer);

        System.out.println(new String(buffer.array()));
        inputStream.close();

    }
}
