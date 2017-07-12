package com.brewery.exceptions;

import com.brewery.utils.ConstantParams;
import com.brewery.utils.ParamUtils;
import com.brewery.utils.ResponseMaker;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionsHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        Map<String, String> errors = ParamUtils.makeErrorMap(ex.getBindingResult().getFieldErrors(), ex.getBindingResult().getGlobalErrors());
        final ApiError apiError = new ApiError(status, ConstantParams.ERROR_MESSAGE, errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }


    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> exceptionHandler(IOException ex) {
        LOGGER.error(ex.getMessage(), ex.getCause());
        return ResponseMaker.makeResponse(
                makeErrorRespJson(ex),
                ConstantParams.JSON_HEADER_TYPE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HibernateException.class)
    public ResponseEntity<String> exceptionHandler(HibernateException ex) {
        LOGGER.error(ex.getMessage(), ex.getCause());
        return ResponseMaker.makeResponse(
                ex.getCause().getLocalizedMessage(),
                ConstantParams.JSON_HEADER_TYPE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<String> exceptionHandler(SQLException ex) {
        LOGGER.error(ex.getMessage(), ex.getCause());
        return ResponseMaker.makeResponse(
                ex.getCause().getLocalizedMessage(),
                ConstantParams.JSON_HEADER_TYPE,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> exceptionHandler(NullPointerException e) {
        LOGGER.error(e.getMessage(), e.getCause());
        return ResponseMaker.makeResponse(
                makeErrorRespJson(e),
                ConstantParams.JSON_HEADER_TYPE,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> exceptionHandler(AccessDeniedException e) {
        LOGGER.error(e.getMessage(), e.getCause());
        return ResponseMaker.makeResponse(
                makeErrorRespJson(e),
                ConstantParams.JSON_HEADER_TYPE, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<String> exceptionHandler(IndexOutOfBoundsException ex) {
        LOGGER.error(ex.getMessage(), ex.getCause());
        return ResponseMaker.makeResponse(makeErrorRespJson(ex),
                ConstantParams.JSON_HEADER_TYPE, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception ex) {
        LOGGER.error(ex.getMessage(), ex.getCause());
        return ResponseMaker.makeResponse(makeErrorRespJson(ex),
                ConstantParams.JSON_HEADER_TYPE, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<String> exceptionHandler(JsonMappingException ex) {
        LOGGER.error(ex.getMessage(), ex.getCause());
        return ResponseMaker.makeResponse(makeErrorRespJson(ex),
                ConstantParams.JSON_HEADER_TYPE, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public ResponseEntity<String> exceptionHandler(IllegalArgumentException ex) {
        LOGGER.error(ex.getMessage(), ex.getCause());
        return ResponseMaker.makeResponse(makeErrorRespJson(ex),
                ConstantParams.JSON_HEADER_TYPE, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<String> exceptionHandler(UserNotFoundException ex) {
        LOGGER.error(ex.getMessage(), ex.getCause());
        return ResponseMaker.makeResponse(makeErrorRespJson(ex),
                ConstantParams.JSON_HEADER_TYPE, HttpStatus.BAD_REQUEST);
    }


    private Map<String, String> makeErrorRespJson(Throwable ex) {
        Map<String, String> error = new HashMap<>();
        error.put(ConstantParams.ERROR_MESSAGE, ex.getLocalizedMessage());
        return error;
    }

}
