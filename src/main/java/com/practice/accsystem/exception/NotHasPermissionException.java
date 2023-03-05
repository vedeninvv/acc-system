package com.practice.accsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotHasPermissionException extends RuntimeException {
    public NotHasPermissionException(String message) {
        super("Not assigned user or not have admin permission to perform action: " + message);
    }
}
