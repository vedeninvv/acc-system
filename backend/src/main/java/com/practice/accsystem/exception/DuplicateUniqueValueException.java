package com.practice.accsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class DuplicateUniqueValueException extends RuntimeException {
    public DuplicateUniqueValueException(String message) {
        super(String.format("Duplicate: %s", message));
    }
}
