package com.guama.purchases.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ProcessPaymentException extends RuntimeException {

    private int statusCode;
    public ProcessPaymentException(String message) {
        super(message);
    }
    public ProcessPaymentException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }
}
