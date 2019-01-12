package com.crxmarkets.rainyhills.controller;

import com.crxmarkets.rainyhills.dto.RainResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RainyHillsController {

    @GetMapping("rain")
    public RainResult rain() {
        //todo add logging everywhere
        return null;
    }
}