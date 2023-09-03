package com.example.demo1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DemoExceptionController {
@ExceptionHandler(value=DemoException.class)
public ResponseEntity<Object> exception(DemoException exception) {
    return new ResponseEntity<>("Age cannot be less than 20", HttpStatus.ACCEPTED);
 }
}
