package com.auth.service.web.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionHandler {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime timestamp;
    private String path;
    private String errorMessage;
    private String statusMessage;
    private int statusCode;

    private HashMap<String, String> erros = new HashMap<>();

    public ExceptionHandler(LocalDateTime timestamp, String path, String errorMessage, String statusMessage, int statusCode) {
        this.timestamp = timestamp;
        this.path = path;
        this.errorMessage = errorMessage;
        this.statusMessage = statusMessage;
        this.statusCode = statusCode;
    }

    public ExceptionHandler(LocalDateTime timestamp, String path, String errorMessage, String statusMessage, int statusCode, BindingResult bindingResult) {
        this.timestamp = timestamp;
        this.path = path;
        this.errorMessage = errorMessage;
        this.statusMessage = statusMessage;
        this.statusCode = statusCode;
        addErros(bindingResult);
    }

    private void addErros(BindingResult bindingResult) {
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();

        for (FieldError error: fieldErrorList) {
            erros.put(error.getField(), error.getDefaultMessage());
        }
    }

}
