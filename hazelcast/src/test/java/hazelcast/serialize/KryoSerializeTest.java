package hazelcast.serialize;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.domain.Person;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class KryoSerializeTest {

    @Test
    public void test1() {
        Kryo kryo = new Kryo();
        kryo.register(Person.class);
        // 创建一个对象
        Person person = new Person("John Doe", 30);

        // 序列化
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Output output = new Output(byteArrayOutputStream);
        kryo.writeObject(output, person);
        output.close();

        byte[] bytes = byteArrayOutputStream.toByteArray();
        System.out.println("Serialized bytes: " + bytes.length);

        // 反序列化
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Input input = new Input(byteArrayInputStream);
        Person deserializedPerson = kryo.readObject(input, Person.class);
        input.close();

        System.out.println("Deserialized object: " + deserializedPerson);
    }

    @Test
    public void testList() {
        Kryo kryo = new Kryo();
        kryo.register(Person.class);
        kryo.register(ArrayList.class); // 注册ArrayList类

        // 创建一个包含多个Person对象的List
        List<Person> people = new ArrayList<>();
        people.add(new Person("John Doe", 30));
        people.add(new Person("Jane Smith", 25));

        // 序列化
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Output output = new Output(byteArrayOutputStream);
        kryo.writeObject(output, people);
        output.close();

        byte[] bytes = byteArrayOutputStream.toByteArray();
        System.out.println("Serialized bytes: " + bytes.length);

        // 反序列化
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Input input = new Input(byteArrayInputStream);
        List<Person> deserializedPeople = kryo.readObject(input, ArrayList.class);
        input.close();

        for (Person person : deserializedPeople) {
            System.out.println("Deserialized object: " + person.toString());
        }
    }

    @Test
    public void testKryoGeneric() {
        Kryo kryo = new Kryo();

    }
}
