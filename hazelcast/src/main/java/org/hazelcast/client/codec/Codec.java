package org.hazelcast.client.codec;


import org.hazelcast.schema.HazelSchema;

import java.io.IOException;

public interface Codec<T> {
    byte[] serialize(T object) throws IOException;

    T deserialize(byte[] binaryData) throws IOException, ClassNotFoundException;
}
