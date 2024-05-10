package org.record.controller;

import org.hazelmap.HMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HazelcastTest {

    private HMap hMap = new HMap();

    @GetMapping("/")
    String home() {
        return "Hello World!";
    }

    @GetMapping("/get")
    Object get(@RequestParam String key) {
        hMap.get(key);
        return key;
    }

    @PostMapping("/put")
    Object put(@RequestParam String key, @RequestParam Object obj) {
        hMap.put(key,obj);
        return "添加成功!";
    }


}