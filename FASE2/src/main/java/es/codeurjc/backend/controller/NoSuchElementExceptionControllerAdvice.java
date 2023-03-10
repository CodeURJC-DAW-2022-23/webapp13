package es.codeurjc.backend.controller;

import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class NoSuchElementExceptionControllerAdvice {
    
    @ExceptionHandler(NoSuchElementException.class)
    public void handleNotFound() {}
}
