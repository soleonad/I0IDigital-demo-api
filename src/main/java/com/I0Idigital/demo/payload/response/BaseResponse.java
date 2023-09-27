package com.I0Idigital.demo.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Collection;

public class BaseResponse {
    @JsonInclude(Include.NON_NULL)
    private Collection<ErrorDetail> errors;

    public Collection<ErrorDetail> getErrors() {
        return errors;
    }

    public void setErrors(Collection<ErrorDetail> errors) {
        this.errors = errors;
    }
}