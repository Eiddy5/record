package org.hazelcast.codec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.hazelcast.client.codec.BaseCodec;
import org.hazelcast.schema.HazelSchema;

import java.io.IOException;

public class JacksonCodec<T> extends BaseCodec<T> {


    private final ObjectMapper objectMapper;

    public JacksonCodec() {
        this.objectMapper = new ObjectMapper();
        init(objectMapper);
    }

    protected void init(ObjectMapper objectMapper) {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setVisibility(objectMapper.getSerializationConfig()
                .getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        objectMapper.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
    }

    @Override
    public byte[] serialize(T object) throws IOException {
        return objectMapper.writeValueAsBytes(object);
    }

    @Override
    public T deserialize(byte[] binaryData) throws IOException {
        return (T) objectMapper.readValue(binaryData, HazelSchema.class);
    }
}
