package com.hyuseinmustafa.librarymanagement.web.v1.controller;

import com.hyuseinmustafa.librarymanagement.Exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.stream.Collectors;

@ControllerAdvice
@ResponseBody
public class ControllerAdvisor extends RuntimeException {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String NotFoundException(){
        return "Content Not Found";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorList validationException(MethodArgumentNotValidException ex){
        return new ValidationErrorList(ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> {
                    ValidationError error = new ValidationError();
                    error.setField(fieldError.getField());
                    error.setMessage(fieldError.getDefaultMessage());
                    return error;
                })
                .collect(Collectors.toList()));
    }
}
