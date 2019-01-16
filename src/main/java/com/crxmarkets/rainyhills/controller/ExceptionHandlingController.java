package com.crxmarkets.rainyhills.controller;

import com.crxmarkets.rainyhills.exception.ArrayParsingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler({ArrayParsingException.class})
    public String arrayError() {

        return "error/arrayParsingError";
    }
}
