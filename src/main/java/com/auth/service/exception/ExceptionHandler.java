package com.auth.service.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExceptionHandler {

    private String timestamp;
    private String path;
    private String statusMessage;
    private int statusCode;

    private HashMap<String, String> erros = new HashMap<>();

    public ExceptionHandler(String path, int statusCode, String statusMessage, String timestamp) {
        this.path = path;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.timestamp = timestamp;
    }

    public ExceptionHandler(String timestamp, String path, String statusMessage, int statusCode, BindingResult bindingResult) {
        this.timestamp = timestamp;
        this.path = path;
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
