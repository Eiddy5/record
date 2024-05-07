package org.hazelcast.codec;

import org.hazelcast.client.codec.Codec;
import org.hazelcast.client.protobuf.Decoder;
import org.hazelcast.client.protobuf.Encoder;

public abstract class BaseCodec implements Codec {
    @Override
    public Decoder<Object> getValueDecoder() {
        return null;
    }

    @Override
    public Encoder getValueEncoder() {
        return null;
    }
}
