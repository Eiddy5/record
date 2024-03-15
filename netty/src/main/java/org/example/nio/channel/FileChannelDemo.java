package org.example.nio.channel;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class FileChannelDemo {


    // 通过fileChannel写入数据到文件
    public static void writeDataToFile() {
        // 需要写入文件的数据
        String str = "hello world";
        try {
            // 创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            ByteBuffer put = buffer.put(bytes);

            // 切换写模式
            put.flip();

            // 通过FileOutputStream获取FileChannel
            FileOutputStream outputStream = new FileOutputStream("test.txt");
            FileChannel channel = outputStream.getChannel();
            // 写入文件
            channel.write(put);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 从文件读出数据
    public static void readDataFromFile() {
        try {
            // 通过FileInputStream获取FileChannel
            File file = new File("test.txt");
            FileInputStream inputStream = new FileInputStream(file);
            FileChannel channel = inputStream.getChannel();
            // 创建buffer
            ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
            channel.read(buffer);

            System.out.println(new String(buffer.array()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyDataToFile() {
        try {
            File file = new File("test.txt");
            // 文件输入Channel
            FileInputStream in = new FileInputStream(file);
            FileChannel inChannel = in.getChannel();

            // 文件输出Channel
            File outFile = new File("test2.txt");
            FileOutputStream out = new FileOutputStream(outFile);
            FileChannel outChannel = out.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(2);

            // transferFrom(ReadableByteChannel src, long position, long count)也可以实现文件的copy
            outChannel.transferFrom(inChannel, 0, inChannel.size());

            while (true) {
                buffer.clear();
                int read = inChannel.read(buffer);
                if (read == -1) {
                    System.out.println("读取完成");
                    break;
                }
                buffer.flip();
                outChannel.write(buffer);
            }
            inChannel.close();
            outChannel.close();
            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        copyDataToFile();
    }

}
