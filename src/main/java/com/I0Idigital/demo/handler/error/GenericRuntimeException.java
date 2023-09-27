package com.I0Idigital.demo.handler.error;

public class GenericRuntimeException extends RuntimeException {
    public GenericRuntimeException() {
    }

    public GenericRuntimeException(String message) {
        super(message);
    }

    public GenericRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public GenericRuntimeException(Throwable cause) {
        super(cause);
    }

    public GenericRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
