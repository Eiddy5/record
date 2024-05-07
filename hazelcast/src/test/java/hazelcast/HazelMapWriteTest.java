package hazelcast;


import org.domain.Person;
import org.hazelcast.HazelMap;
import org.junit.jupiter.api.Test;

public class HazelMapWriteTest {

    @Test
    public void testWriteMap() {
        Person person = new Person();
        HazelMap<Person> use = HazelMap.use(Person.class);
        Person test = use.write("test", person);
        Person getPerson = use.get("test");
        System.out.println(getPerson.toString());
    }

}
