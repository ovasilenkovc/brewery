package com.brewery.exceptions;

import com.brewery.utils.ConstantParams;
import com.brewery.utils.ParamUtils;
import com.brewery.utils.ResponseMaker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = Logger.getLogger(ExceptionsHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        Map<String, String> errors = ParamUtils.makeErrorMap(ex.getBindingResult().getFieldErrors(), ex.getBindingResult().getGlobalErrors());
        final ApiError apiError = new ApiError(status, ConstantParams.ERROR_MESSAGE, errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        String exceptionClassName = ex.getCause().getClass().getName();
        String exceptionCauseMessage = ex.getCause().getMessage();
        errors.put(exceptionClassName, exceptionCauseMessage);
        final ApiError apiError = new ApiError(status, ConstantParams.ERROR_MESSAGE, errors);
        return handleExceptionInternal(ex, apiError, headers, status, request);
    }


    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> exceptionHandler(IOException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return ResponseMaker.makeResponse(makeErrorRespJson(ex), ConstantParams.JSON_HEADER_TYPE, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HibernateException.class)
    public ResponseEntity<String> exceptionHandler(HibernateException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return ResponseMaker.makeResponse(ex.getCause().getMessage(), ConstantParams.JSON_HEADER_TYPE,
                HttpStatus.INTERNAL_SERVER_ERROR, ConstantParams.ERROR_MESSAGE);
    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<String> exceptionHandler(PropertyValueException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return ResponseMaker.makeResponse(ex.getMessage(), ConstantParams.JSON_HEADER_TYPE,
                HttpStatus.INTERNAL_SERVER_ERROR, ConstantParams.ERROR_MESSAGE);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> exceptionHandler(SQLException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return ResponseMaker.makeResponse(ex.getCause().getLocalizedMessage(),
                ConstantParams.JSON_HEADER_TYPE, HttpStatus.INTERNAL_SERVER_ERROR, ConstantParams.ERROR_MESSAGE);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> exceptionHandler(DataIntegrityViolationException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return ResponseMaker.makeResponse(ex.getCause().getCause().getMessage(),
                ConstantParams.JSON_HEADER_TYPE, HttpStatus.INTERNAL_SERVER_ERROR, ConstantParams.ERROR_MESSAGE);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> exceptionHandler(NullPointerException e) {
        LOGGER.error(e.getMessage(), e);
        return ResponseMaker.makeResponse(makeErrorRespJson(e), ConstantParams.JSON_HEADER_TYPE, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> exceptionHandler(AccessDeniedException e) {
        LOGGER.error(e.getMessage(), e);
        return ResponseMaker.makeResponse(makeErrorRespJson(e), ConstantParams.JSON_HEADER_TYPE, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<String> exceptionHandler(IndexOutOfBoundsException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return ResponseMaker.makeResponse(makeErrorRespJson(ex), ConstantParams.JSON_HEADER_TYPE, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        return ResponseMaker.makeResponse(makeErrorRespJson(ex), ConstantParams.JSON_HEADER_TYPE, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<String> exceptionHandler(JsonMappingException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return ResponseMaker.makeResponse(makeErrorRespJson(ex), ConstantParams.JSON_HEADER_TYPE, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<String> exceptionHandler(IllegalArgumentException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return ResponseMaker.makeResponse(makeErrorRespJson(ex), ConstantParams.JSON_HEADER_TYPE, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> exceptionHandler(UserNotFoundException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return ResponseMaker.makeResponse(makeErrorRespJson(ex), ConstantParams.JSON_HEADER_TYPE, HttpStatus.BAD_REQUEST);
    }

    private Map<String, String> makeErrorRespJson(Throwable ex) {
        Map<String, String> error = new HashMap<>();
        error.put(ConstantParams.ERROR_MESSAGE, ex.getLocalizedMessage());
        return error;
    }

}
