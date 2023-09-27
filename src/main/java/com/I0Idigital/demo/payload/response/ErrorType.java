package com.I0Idigital.demo.payload.response;

public enum ErrorType {
    FIELD_ERROR("field_error"), RECORD_NOT_EXISTED_ERROR("record_not_existed_error"), BUSINESS_CONSTRAIN_ERROR(
            "business_constrain_error");
    private String code;

    private ErrorType(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.code;
    }
}