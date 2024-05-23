package hazelcast.serialize;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.domain.IdName;
import org.domain.NameValue;
import org.hazelcast.client.codec.Codec;
import org.hazelcast.codec.JacksonCodec;
import org.junit.jupiter.api.Test;

import java.io.IOException;


public class SerializeTest {

    @Test
    public void testJackson() throws IOException {
        IdName john = IdName.Of("1", "john");
        JacksonCodec<IdName> jacksonCodec = new JacksonCodec<>();
        String serialize = jacksonCodec.serialize(john);
        System.out.println(serialize);
        JacksonCodec<NameValue> codec = new JacksonCodec<>();
        NameValue deserialize = codec.deserialize(serialize, NameValue.class);
        System.out.println(deserialize);
    }

    @Test
    public void testCodecSchema() throws IOException {
    }
}
