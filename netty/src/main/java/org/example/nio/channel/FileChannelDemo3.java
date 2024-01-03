package org.example.nio.channel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo3 {
    public static void main(String[] args) throws IOException {

        /*
         * 使用同一个buffer，将test.txt中的内容copy到test2.txt中
         * */

        File file = new File("/workspace/test/test.txt");
        FileInputStream inputStream = new FileInputStream(file);
        FileChannel channel01 = inputStream.getChannel();

        FileOutputStream outputStream = new FileOutputStream("/workspace/test/test2.txt");
        FileChannel channel02 = outputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(128);

        while (true) {
            buffer.clear();
            int read = channel01.read(buffer);
            System.out.println("read = " + read);
            if (read == -1) {
                System.out.println("读取完成！");
                break;
            }
            buffer.flip();
            channel02.write(buffer);
        }

        // 关闭流
        inputStream.close();
        outputStream.close();


    }
}
