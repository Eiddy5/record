package hazelcast.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.domain.Person;
import org.junit.jupiter.api.Test;


public class SerializeTest {

    @Test
    public void testJackson() throws JsonProcessingException {
        Person person = new Person();
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(person);
        System.out.println(s);
    }
}
