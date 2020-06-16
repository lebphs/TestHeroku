package com.example.demo

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/hello")
class TestRestService {
    @GetMapping
    fun hello():String{
        return "Hello word!";
    }
}