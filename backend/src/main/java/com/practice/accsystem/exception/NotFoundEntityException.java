package com.practice.accsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundEntityException extends RuntimeException {
    public NotFoundEntityException(String message) {
        super(String.format("NotFound: %s", message));
    }
}
