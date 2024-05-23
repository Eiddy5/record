package hazelcast.serialize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.domain.Person;
import org.hazelcast.HazelMap;
import org.hazelcast.codec.JacksonCodec;
import org.hazelcast.schema.HazelMapSchema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


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
        HazelMapSchema schema = new HazelMapSchema(List.of(new Person()));
        JacksonCodec<Object> codec = new JacksonCodec<>();
        byte[] serialize = codec.serialize(schema);
        System.out.println(Arrays.toString(serialize));



    }

    @Test
    public void testHazelMapWrite(){
        HazelMap<Person> hazelMap = HazelMap.use(Person.class);

        Person person = new Person("John", 30);
        hazelMap.write("personKey", person, 1, TimeUnit.HOURS, 1, TimeUnit.HOURS);

        Person retrievedPerson = hazelMap.read("personKey");
        Assertions.assertNotNull(retrievedPerson);
        Assertions.assertEquals("John", retrievedPerson.getName());
        Assertions.assertEquals(30, retrievedPerson.getAge());

        List<Person> personList = Arrays.asList(new Person("Alice", 25), new Person("Bob", 28));
        hazelMap.writeList("personListKey", personList, 1, TimeUnit.HOURS, 1, TimeUnit.HOURS);

        List<Person> retrievedPersonList = hazelMap.readList("personListKey", Person.class);
        Assertions.assertNotNull(retrievedPersonList);
        Assertions.assertEquals(2, retrievedPersonList.size());
        Assertions.assertEquals("Alice", retrievedPersonList.get(0).getName());
        Assertions.assertEquals(25, retrievedPersonList.get(0).getAge());
        Assertions.assertEquals("Bob", retrievedPersonList.get(1).getName());
        Assertions.assertEquals(28, retrievedPersonList.get(1).getAge());
    }

}
