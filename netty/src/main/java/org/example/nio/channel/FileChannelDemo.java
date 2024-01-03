package org.example.nio.channel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class FileChannelDemo {
    public static void main(String[] args) throws IOException {

        String str = "hello world";

        FileOutputStream outputStream = new FileOutputStream("/workspace/test/test.txt");
        FileChannel channel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(str.getBytes(StandardCharsets.UTF_8));

        buffer.flip();

        channel.write(buffer);
        outputStream.close();
    }
}
