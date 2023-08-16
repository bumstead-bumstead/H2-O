package com.h2o.h2oServer.global.exception.exceptionHandler;

import com.h2o.h2oServer.global.exception.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleError(RuntimeException e) {
        ErrorResponse errorResponse = ErrorResponse.of(e.getMessage());

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
