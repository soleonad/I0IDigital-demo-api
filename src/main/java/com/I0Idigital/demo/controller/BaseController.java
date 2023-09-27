package com.I0Idigital.demo.controller;

import com.I0Idigital.demo.payload.response.BaseResponse;
import com.I0Idigital.demo.payload.response.ErrorDetail;
import com.I0Idigital.demo.payload.response.ErrorType;
import com.I0Idigital.demo.handler.error.NoAvailableQueueError;
import com.I0Idigital.demo.handler.error.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class BaseController {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public BaseResponse handleRecordNotFoundExceptions(NotFoundException ex) {
        Collection<ErrorDetail> errors = new ArrayList<>();
        Map<String, String> details = new HashMap<>();
        details.put("reason", ex.getMessage());
        errors.add(new ErrorDetail(ErrorType.RECORD_NOT_EXISTED_ERROR, "Record not found!", details));
        BaseResponse response = new BaseResponse();
        response.setErrors(errors);
        return response;
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NoAvailableQueueError.class)
    public BaseResponse handleValidationExceptions(NoAvailableQueueError ex) {
        Collection<ErrorDetail> errors = new ArrayList<>();
        Map<String, String> details = new HashMap<>();
        details.put("reason", ex.getMessage());
        errors.add(new ErrorDetail(ErrorType.BUSINESS_CONSTRAIN_ERROR, "Process error due to a business rule!", details));
        BaseResponse response = new BaseResponse();
        response.setErrors(errors);
        return response;
    }
}