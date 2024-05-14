package org.hazelcast.codec;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.hazelcast.client.codec.BaseCodec;
import org.hazelcast.client.codec.Codec;
import org.hazelcast.client.protobuf.Decoder;
import org.hazelcast.client.protobuf.Encoder;
import org.hazelcast.client.protobuf.JacksonDecoder;
import org.hazelcast.client.protobuf.JacksonEncoder;
import org.hazelcast.metadata.HazelMetadata;
import java.io.IOException;

public class JacksonCodec extends BaseCodec implements Codec {

    private final Encoder encoder;
    private final Decoder<HazelMetadata> decoder;

    public JacksonCodec() {
        ObjectMapper objectMapper = new ObjectMapper();
        init(objectMapper);
        this.encoder = new JacksonEncoder(objectMapper);
        this.decoder = new JacksonDecoder(objectMapper);
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
    public Decoder<HazelMetadata> getValueDecoder() {
        return decoder;
    }

    @Override
    public Encoder getValueEncoder() {
        return encoder;
    }
}
