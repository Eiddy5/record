package org.hazelcast.client.codec;

import org.hazelcast.schema.HazelSchema;

import java.io.*;

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
