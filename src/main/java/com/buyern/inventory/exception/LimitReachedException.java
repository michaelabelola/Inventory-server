package com.buyern.inventory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class LimitReachedException extends RuntimeException {

    public LimitReachedException(String message) {
        super(message);
    }
}
