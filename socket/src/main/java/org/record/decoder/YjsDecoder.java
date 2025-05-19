package org.record.decoder;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class YjsDecoder {
    public static void decode(byte[] data) {
        ByteBuffer buffer = ByteBuffer.wrap(data);

        // 跳过控制字符（如 0x1e）
        if (buffer.get(0) == 0x1e) {
            buffer.position(1);
        }

        // 读取 messageType
        int messageType = Byte.toUnsignedInt(buffer.get());
//        System.out.println("Message Type: " + messageType);

        if (messageType == 0){
            System.out.println(Arrays.toString(data));
            System.out.println("Message content: " + new String(data, StandardCharsets.UTF_8));
        }

    }

}
