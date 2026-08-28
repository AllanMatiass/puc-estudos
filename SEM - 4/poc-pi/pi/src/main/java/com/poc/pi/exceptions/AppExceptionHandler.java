package com.poc.pi.exceptions;

import com.poc.pi.domains.dtos.responses.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

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

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(ResponseStatusException e){
        ErrorResponse response = new ErrorResponse(e.getStatusCode().value(), e.getReason(), e.getHeaders().getOrigin());
        return ResponseEntity.status(e.getStatusCode()).body(response);
    }
}
