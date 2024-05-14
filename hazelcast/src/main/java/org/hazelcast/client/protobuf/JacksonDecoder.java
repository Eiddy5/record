package org.hazelcast.client.protobuf;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hazelcast.metadata.HazelMetadata;

import java.io.IOException;

public class JacksonDecoder implements Decoder<HazelMetadata>{

    private final ObjectMapper objectMapper;

    public JacksonDecoder() {
        objectMapper = new ObjectMapper();
    }
    public JacksonDecoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    @Override
    public HazelMetadata decode(String str) throws IOException {
        return objectMapper.readValue(str, HazelMetadata.class);
    }
}
