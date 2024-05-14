package hazelcast.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.domain.Person;
import org.hazelcast.client.protobuf.Decoder;
import org.hazelcast.client.protobuf.Encoder;
import org.hazelcast.codec.JacksonCodec;
import org.hazelcast.metadata.HazelMetadata;
import org.hazelcast.metadata.MetadataEnum;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;


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
        JacksonCodec codec = new JacksonCodec();
        Encoder encoder = codec.getValueEncoder();
        HazelMetadata metadata = new HazelMetadata(MetadataEnum.Object, List.of(person));
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(metadata));
        String string = encoder.encode(metadata);
        System.out.println(string);
        Decoder<HazelMetadata> decoder = codec.getValueDecoder();
        HazelMetadata decode = decoder.decode(string);
        System.out.println(decode);
        Person value = decode.getValue();
        System.out.println(value);
    }

}
