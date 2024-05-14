package org.hazelcast.client.protobuf;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JacksonEncoder implements Encoder {

    private final ObjectMapper objectMapper;

    public JacksonEncoder() {
        objectMapper = new ObjectMapper();
    }
    public JacksonEncoder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String encode(Object in) throws IOException {
        String s = objectMapper.writeValueAsString(in);
        System.out.println(s);
        return "";
    }
}
