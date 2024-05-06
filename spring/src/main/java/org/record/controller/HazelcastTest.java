package org.record.controller;

import org.record.domain.Person;
import org.record.hazelcast.HazelcastClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HazelcastTest {

    @Autowired
    HazelcastClient client;

    @GetMapping("/")
    String home() {
        return "Hello World!";
    }

    @GetMapping("/get/{key}")
    Object get(@PathVariable String key) {
        return null;
    }

    @PostMapping("/put")
    Object put(@RequestBody Person person) {
        return "添加成功!";
    }


}
