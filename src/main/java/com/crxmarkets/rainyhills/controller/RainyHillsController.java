package com.crxmarkets.rainyhills.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RainyHillsController {

    @GetMapping("hello")
    public String helloWorld() {
        return "hello!";
    }
}