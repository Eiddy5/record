package org.hazelcast.client.codec;

public interface Encoder {
    String encode(Object in);
}
