package org.hazelcast.client.codec;

import org.hazelcast.client.protobuf.Decoder;
import org.hazelcast.client.protobuf.Encoder;

public interface Codec {

    Decoder<Object> getMapValueDecoder();

    Encoder getMapValueEncoder();

    Decoder<Object> getMapKeyDecoder();

    Encoder getMapKeyEncoder();

    Decoder<Object> getValueDecoder();

    Encoder getValueEncoder();

    ClassLoader getClassLoader();

}
