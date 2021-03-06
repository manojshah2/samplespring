package com.manoj.app.sampleproject.web.exceptions;


import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.validation.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }


    /**
     * Handle NoSuchElementException
     **/
    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<Object> handleEntityNotFound(NoSuchElementException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        apiError.setMessage(apiError.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
        //apiError.setMessage(Translator.toLocale(apiError.getMessage(), null));
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<Object> handleAllExceptions(IllegalArgumentException ex, final WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setDebugMessage("Debug it");
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(RequestValidationException.class)
    public final ResponseEntity<Object> validationEx(RequestValidationException ex, final WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        apiError.setSubErrors(new ArrayList<ApiValidationError>());
        for (FieldError fe : ex.getErrorFields()) {
            apiError.getSubErrors()
                    .add(ApiValidationError.builder()
                            .field(fe.getField())
                            .message(fe.getDefaultMessage())
                            .object(fe.getObjectName())
                            .rejectedValue(fe.getRejectedValue())
                            .build());
        }
        return buildResponseEntity(apiError);
    }

    /**
     * Handle JPA Exceptions
     *
     * @category JPA Exception
     **/
    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleJPAExceptions(ConstraintViolationException ex, final WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getCause().getMessage());
        return buildResponseEntity(apiError);
    }



}

