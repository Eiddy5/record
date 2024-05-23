package org.record.controller;

import org.hazelcast.HazelMap;
import org.record.domain.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HazelcastTest {


    @GetMapping("/")
    String home() {
        return "Hello World!";
    }

    @GetMapping("/get")
    Object get(@RequestParam String key) {
        return HazelMap.use(Person.class).read(key);
    }

    @PostMapping("/put")
    Object put(@RequestParam String key, @RequestParam Person obj) {
        boolean b = HazelMap.use(Person.class).write(obj.getName(), obj);
        if (b){
            return "添加成功!";
        }
        return "添加失败!";
    }


}
