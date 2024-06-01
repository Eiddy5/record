package org.hazelcast.codec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.okr.server.hazelcast.client.codec.Decoder;
import org.okr.server.hazelcast.client.codec.Encoder;

public class JsonJacksonCodec {

    public static final JsonJacksonCodec INSTANCE = new JsonJacksonCodec();

    protected final ObjectMapper mapObjectMapper;

    private final Encoder encoder = new Encoder() {
        @Override
        public String encode(Object in) {
            try {
                return mapObjectMapper.writeValueAsString(in);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    };

    private final Decoder<Object> decoder = new Decoder<Object>() {
        @Override
        public Object decode(String in) {
            try {
                return mapObjectMapper.readValue(in, Object.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    };

    public JsonJacksonCodec() {
        this(new ObjectMapper());
    }

    public JsonJacksonCodec(ObjectMapper mapObjectMapper) {
        this.mapObjectMapper = mapObjectMapper;
        init(mapObjectMapper);
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
    }

    public ObjectMapper getObjectMapper() {
        return mapObjectMapper;
    }
}
