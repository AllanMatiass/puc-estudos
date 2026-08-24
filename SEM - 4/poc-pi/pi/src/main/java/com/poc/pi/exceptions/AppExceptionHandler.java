package com.poc.pi.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception e){
        // por questões de segurança, não exibimos o que aconteceu quando o erro é 500
        return ResponseEntity.internalServerError().body("Internal Server Error");
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(IllegalArgumentException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
