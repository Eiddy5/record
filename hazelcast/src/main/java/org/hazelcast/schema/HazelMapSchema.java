package org.hazelcast.schema;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;

public abstract class HazelMapSchema<T> {

    private MetadataEnum type;

    public HazelMapSchema() {
    }

    public HazelMapSchema(MetadataEnum type) {
        this.type = type;
    }

    public abstract byte[] serialize() throws IOException;

    public abstract T deserialize(byte[] binaryData, TypeReference<T> typeRef) throws IOException;
}
