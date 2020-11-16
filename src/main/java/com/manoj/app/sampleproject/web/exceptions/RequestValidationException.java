package com.manoj.app.sampleproject.web.exceptions;

import java.util.List;

import org.springframework.validation.FieldError;

public class RequestValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private List<FieldError> errorFields;

    public RequestValidationException(String objectName, List<FieldError> errorFields) {
        super(String.format("Validation failed for %s.", objectName));
        this.errorFields = errorFields;
    }

    public List<FieldError> getErrorFields() {
        return errorFields;
    }

    public void setErrorFields(List<FieldError> errorFields) {
        this.errorFields = errorFields;
    }
}


