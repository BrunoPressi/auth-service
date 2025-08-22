package com.auth.service.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<com.auth.service.exception.ExceptionHandler> methodArgumentNotValidException(HttpServletRequest request, BindingResult bindingResult) {

        LocalDateTime timestamp = LocalDateTime.now();
        String path = request.getRequestURI();
        String errorMessage = "Invalid input data";
        String statusMessage = HttpStatus.BAD_REQUEST.getReasonPhrase();
        int statusCode = HttpStatus.BAD_REQUEST.value();

        com.auth.service.exception.ExceptionHandler exceptionHandler = new com.auth.service.exception.ExceptionHandler(
                timestamp,
                path,
                errorMessage,
                statusMessage,
                statusCode,
                bindingResult
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST.value())
                .body(exceptionHandler);

    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<com.auth.service.exception.ExceptionHandler> usernameNotFoundException(HttpServletRequest request, RuntimeException e) {

        LocalDateTime timestamp = LocalDateTime.now();
        String path = request.getRequestURI();
        String errorMessage = e.getMessage();
        String statusMessage = HttpStatus.UNAUTHORIZED.getReasonPhrase();
        int statusCode = HttpStatus.UNAUTHORIZED.value();

        com.auth.service.exception.ExceptionHandler exceptionHandler = new com.auth.service.exception.ExceptionHandler(
                timestamp,
                path,
                errorMessage,
                statusMessage,
                statusCode
        );

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED.value())
                .body(exceptionHandler);

    }

}
