package org.hazelcast.schema;


import com.fasterxml.jackson.core.type.TypeReference;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.hazelcast.codec.JacksonCodec;

import java.io.IOException;
import java.time.OffsetDateTime;

public class HazelMetadata<T> extends HazelMapSchema<HazelMetadata<T>> {

    private MetadataEnum type;
    private T value;

    public HazelMetadata() {
    }

    public HazelMetadata(MetadataEnum type, T value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public byte[] serialize() throws IOException {
        JacksonCodec<HazelMetadata<T>> codec = new JacksonCodec<>(new TypeReference<>() {
        });
        ByteBuf byteBuf = codec.getValueEncoder().encode(this);
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        return bytes;

    }

    @Override
    public HazelMetadata<T> deserialize(byte[] binaryData, TypeReference<HazelMetadata<T>> typeRef) throws IOException {
        JacksonCodec<HazelMetadata<T>> codec = new JacksonCodec<>(typeRef);
        ByteBuf byteBuf = Unpooled.wrappedBuffer(binaryData);
        return codec.getValueDecoder().decode(byteBuf);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }


}
