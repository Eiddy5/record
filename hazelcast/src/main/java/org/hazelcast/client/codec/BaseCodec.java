package org.hazelcast.client.codec;

import com.esotericsoftware.kryo.Kryo;
import org.hazelcast.schema.HazelSchema;

import java.io.*;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BaseCodec<T> implements Codec<T>{
    @Override
    public byte[] serialize(T object) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(object);
            return bos.toByteArray();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T deserialize(byte[] binaryData) throws IOException, ClassNotFoundException {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(binaryData);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (T) ois.readObject();
        }
    }
}
