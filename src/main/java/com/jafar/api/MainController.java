package com.jafar.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public String home(){
        return "안녕하세요";
    }


    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
