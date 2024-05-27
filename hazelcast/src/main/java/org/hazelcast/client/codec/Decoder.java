package org.hazelcast.client.codec;

public interface Decoder<R> {
    R decode(String in);
}
