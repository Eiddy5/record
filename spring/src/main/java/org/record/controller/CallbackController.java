package org.record.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallbackController{
    @GetMapping("/callback")
    public String test(){
        System.out.println("callback");
        return "callback";
    }
}
