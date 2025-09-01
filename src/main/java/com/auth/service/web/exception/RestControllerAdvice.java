package com.auth.service.web.exception;

import com.auth.service.exceptions.EmailNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@org.springframework.web.bind.annotation.RestControllerAdvice
public class RestControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<com.auth.service.web.exception.ExceptionHandler> methodArgumentNotValidException(HttpServletRequest request, BindingResult bindingResult) {

        LocalDateTime timestamp = LocalDateTime.now();
        String path = request.getRequestURI();
        String errorMessage = "Invalid input data";
        String statusMessage = HttpStatus.BAD_REQUEST.getReasonPhrase();
        int statusCode = HttpStatus.BAD_REQUEST.value();

        com.auth.service.web.exception.ExceptionHandler exceptionHandler = new com.auth.service.web.exception.ExceptionHandler(
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

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<com.auth.service.web.exception.ExceptionHandler> emailNotFoundException(HttpServletRequest request, RuntimeException e) {

        LocalDateTime timestamp = LocalDateTime.now();
        String path = request.getRequestURI();
        String errorMessage = e.getMessage();
        String statusMessage = HttpStatus.NOT_FOUND.getReasonPhrase();
        int statusCode = HttpStatus.NOT_FOUND.value();

        com.auth.service.web.exception.ExceptionHandler exceptionHandler = new com.auth.service.web.exception.ExceptionHandler(
                timestamp,
                path,
                errorMessage,
                statusMessage,
                statusCode
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND.value())
                .body(exceptionHandler);

    }


}
