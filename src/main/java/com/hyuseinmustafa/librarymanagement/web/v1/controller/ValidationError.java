package com.hyuseinmustafa.librarymanagement.web.v1.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ValidationError {
    private String field;
    private String message;
}
