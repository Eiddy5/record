package org.hazelcast.client.codec;


import org.hazelcast.schema.HazelSchema;

import java.io.IOException;

public interface Codec<T> {
    byte[] serialize(HazelSchema object) throws IOException;
    HazelSchema deserialize(byte[] binaryData) throws IOException, ClassNotFoundException;
}
