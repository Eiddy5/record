package org.hazelcast.metadata;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;

public abstract class BaseMetadata<T> {
    public abstract byte[] serialize() throws IOException;

    public abstract T deserialize(byte[] binaryData, TypeReference<T> typeRef) throws IOException;
}
