package org.serializer;

import org.domain.Person;
import org.hazelcast.schema.HazelSchema;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            // 创建一个 HazelSchema<Person> 对象
            HazelSchema<Person> schema = new HazelSchema<>();
            Person person = new Person();
            person.setName("John");
            person.setAge(30);
            schema.setList(false);
            schema.setData(person);

            // 序列化 HazelSchema<Person> 对象
            String jsonString = JsonUtil.serialize(schema);
            System.out.println("Serialized JSON: " + jsonString);

            // 使用通用方法反序列化 HazelSchema<Person> 对象
            HazelSchema<Person> deserializedSchema = JsonUtil.deserialize(jsonString, Person.class);
            Person data = deserializedSchema.getData();
            System.out.println("Deserialized data: " + data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
