package org.record.controller;

import org.client.HazelcastClient;
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

    @GetMapping("/get")
    Object get(@RequestParam String key) {
        Object o = client.get(key);
        return o;
    }

    @PostMapping("/put")
    Object put(@RequestParam String key, @RequestParam Object obj) {
        Object put = client.put(key, obj);
        return "添加成功!";
    }


}
