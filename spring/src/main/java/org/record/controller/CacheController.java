package org.record.controller;

import org.record.domain.Person;
import org.springframework.web.bind.annotation.*;

@RestController
public class CacheController {


    @GetMapping("/")
    String home() {
        return "Hello World!";
    }

    @GetMapping("/get/{key}")
    Object get(@PathVariable String key) {
        return IgniteStore.use(Person.class).read(key);
    }

    @PostMapping("/put")
    Object put(@RequestBody Person obj) {
        boolean b = IgniteStore.use(Person.class).write(obj.getName(), obj);
        if (b) {
            return "添加成功!";
        }
        return "添加失败!";
    }


}
