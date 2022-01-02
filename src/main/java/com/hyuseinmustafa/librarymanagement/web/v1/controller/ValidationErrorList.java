package com.hyuseinmustafa.librarymanagement.web.v1.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class ValidationErrorList {
    private List<ValidationError> validationErrors;
}
