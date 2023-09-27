package com.I0Idigital.demo.payload.response;

import java.util.Map;

public class ErrorDetail {
    private ErrorType type;
    private Map<String, String> details;
    private String message;

    public ErrorDetail(ErrorType type, String message, Map<String, String> details) {
        this.type = type;
        this.details = details;
        this.message = message;
    }

    public ErrorType getType() {
        return type;
    }

    public void setType(ErrorType type) {
        this.type = type;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}