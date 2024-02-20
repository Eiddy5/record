package com.engine.ext;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        HashMap<String, List<Object>> map = new HashMap<>();
        map.put("info", List.of("city", "age", "a", "b", "c", "d", "e", "f", "g", "h"));

        String string = """
                {
                    "id": "123",
                    "name": "张三",
                    "city": "北京",
                    "age": 18,
                    "a": 1,
                    "b": 2,
                    "c": 3,
                    "d": 4,
                    "e": 5,
                    "f": 6,
                    "g": 7,
                    "h": 8
                }
                """;
        JSONObject jsonObject = JSONObject.parseObject(string, Feature.IgnoreNotMatch);
        Person person = jsonObject.toJavaObject(Person.class);

        jsonObject.keySet().stream()
                .filter(key -> map.getOrDefault("info", List.of()).contains(key))
                .forEach(key -> person.getExt().put(key, jsonObject.get(key)));


        System.out.println(person);
    }
}
