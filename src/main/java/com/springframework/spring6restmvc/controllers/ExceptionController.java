package com.springframework.spring6restmvc.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
   @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(){
       return ResponseEntity.notFound().build();
   }
}
