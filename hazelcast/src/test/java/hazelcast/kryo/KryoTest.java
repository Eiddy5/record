package hazelcast.kryo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.domain.Person;
import org.junit.jupiter.api.Test;


public class KryoTest {

    @Test
    public void testKryo() throws JsonProcessingException {
        Person person = new Person();
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(person);
        System.out.println(s);
    }
}
