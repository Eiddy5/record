package org.hazelcast.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.util.CharsetUtil;
import org.hazelcast.client.codec.JsonCodec;
import org.hazelcast.client.protobuf.Decoder;
import org.hazelcast.client.protobuf.Encoder;
import org.hazelcast.metadata.HazelMetadata;

import java.io.IOException;
import java.nio.charset.Charset;

public class StringCodec extends BaseCodec implements JsonCodec<String> {

    public static final StringCodec INSTANCE = new StringCodec();
    private final Charset charset;
    private final Encoder encoder = new Encoder() {
        @Override
        public ByteBuf encode(Object in) throws IOException {
            ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
            out.writeCharSequence(in.toString(), charset);
            return out;
        }
    };

    private final Decoder<Object> decoder = new Decoder<>() {
        @Override
        public Object decode(ByteBuf buf, HazelMetadata state) {
            String str = buf.toString(charset);
            buf.readerIndex(buf.readableBytes());
            return str;
        }
    };

    public StringCodec() {
        this(CharsetUtil.UTF_8);
    }

    public StringCodec(ClassLoader classLoader) {
        this();
    }

    public StringCodec(String charsetName) {
        this(Charset.forName(charsetName));
    }

    public StringCodec(Charset charset) {
        this.charset = charset;
    }


    @Override
    public Decoder<Object> getValueDecoder() {
        return decoder;
    }

    @Override
    public Encoder getValueEncoder() {
        return encoder;
    }
}
