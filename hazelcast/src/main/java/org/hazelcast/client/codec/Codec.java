package org.hazelcast.client.codec;

import org.hazelcast.client.protobuf.Decoder;
import org.hazelcast.client.protobuf.Encoder;

public interface Codec<T> {

    Decoder<T> getValueDecoder();

    Encoder getValueEncoder();


}
