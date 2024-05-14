package hazelcast;


import org.domain.Person;
import org.hazelcast.HazelMap;
import org.junit.jupiter.api.Test;

import java.util.List;

public class HazelMapWriteTest {

    @Test
    public void testWriteMap() {
        Person person = new Person();
        HazelMap<Person> use = HazelMap.use(Person.class);
        boolean b = use.write("test", List.of(person));
        System.out.println(b);
        Person getPerson = use.getOne("test");
        System.out.println(getPerson.toString());
    }

}
