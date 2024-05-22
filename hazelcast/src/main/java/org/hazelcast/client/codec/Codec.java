package org.hazelcast.client.codec;


import java.io.IOException;

public interface Codec<T> {
    String serialize(T object) throws IOException;

    T deserialize(String binaryData) throws IOException, ClassNotFoundException;
}
