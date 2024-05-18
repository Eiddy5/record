package org.hazelcast.client.codec;

import org.hazelcast.client.protobuf.Decoder;
import org.hazelcast.client.protobuf.Encoder;

public abstract class BaseCodec<T> implements Codec<T> {
    @Override
    public Decoder<T> getValueDecoder() {
        return null;
    }

    @Override
    public Encoder getValueEncoder() {
        return null;
    }
}
