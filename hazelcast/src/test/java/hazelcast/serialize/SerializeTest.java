package hazelcast.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import org.domain.Person;
import org.hazelcast.codec.JacksonCodec;
import org.hazelcast.metadata.HazelMetadata;
import org.hazelcast.metadata.MetadataEnum;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
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
        List<Person> persons = List.of(new Person());
        HazelMetadata<List<Person>> metadata = new HazelMetadata<>(MetadataEnum.Collection, persons);
        byte[] serialize = metadata.serialize();
        System.out.println(Arrays.toString(serialize));
        HazelMetadata<List<Person>> deserialize = metadata.deserialize(serialize, new TypeReference<>() {
        });
        System.out.println(deserialize);
    }

}
