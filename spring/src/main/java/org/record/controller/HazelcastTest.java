package org.record.controller;

import org.record.hazelcast.client.HazelcastClient;
import org.springframework.web.bind.annotation.*;

@RestController
public class HazelcastTest {


    @GetMapping("/get")
    Object get(@RequestParam String key) {
        Object o = HazelcastClient.Use().get(key);
        return o;
    }

    @PostMapping("/put")
    Object put(@RequestParam String key, @RequestParam Object obj) {
        Object put = HazelcastClient.Use().put(key, obj);
        return "添加成功!";
    }
}
