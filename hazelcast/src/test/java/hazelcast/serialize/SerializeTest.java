package hazelcast.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.StringUtil;
import org.domain.Person;
import org.hazelcast.client.protobuf.Decoder;
import org.hazelcast.client.protobuf.Encoder;
import org.hazelcast.codec.StringCodec;
import org.hazelcast.metadata.HazelMetadata;
import org.junit.jupiter.api.Test;

import java.io.IOException;


public class SerializeTest {

    @Test
    public void testJackson() throws JsonProcessingException {
        Person person = new Person();
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(person);
        System.out.println(s);
    }

    @Test
    public void testJackCodec() throws IOException {
        Person person = new Person();
        StringCodec codec = new StringCodec();
        Encoder encoder = codec.getValueEncoder();
        ByteBuf encode = encoder.encode(person);
        byte b = encode.readByte();
        Decoder<Object> decoder = codec.getValueDecoder();

        Object decode = decoder.decode(encode, null);
        System.out.println(decode);
    }

}
