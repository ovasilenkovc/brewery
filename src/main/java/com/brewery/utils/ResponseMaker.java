package com.brewery.utils;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper class for making new ResponseEntity.class
 * with all needed header parameters, status code and obtained content.
 */
public class ResponseMaker {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseMaker.class);

    /**
     * Method for making JSON response object from an obtained Object.
     * @param args response object
     * @param contentType value for the Content-Type header.
     * @param status Http status.
     */
    public static ResponseEntity<String> makeResponse(Object args, String contentType, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(ConstantParams.CONTENT_TYPE_HEADER, contentType);

        try {
            ObjectMapper mapper = new ObjectMapper();
            return new ResponseEntity<>(mapper.writeValueAsString(args), headers, status);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getLocalizedMessage(), headers, status);
        }
    }

    /**
     * Method for making JSON response message string from an obtained Java String.
     * @param message response message string.
     * @param contentType value for the Content-Type header.
     * @param status Http status.
     */
    public static ResponseEntity<String> makeResponse(String message, String contentType, HttpStatus status) {
        ObjectMapper mapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> args = new HashMap<>();

        try {
            args.put(ConstantParams.INFO_MESSAGE, message);
            headers.add(ConstantParams.CONTENT_TYPE_HEADER, contentType);
            return new ResponseEntity<>(mapper.writeValueAsString(args), headers, status);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(e.getLocalizedMessage(), headers, status);
        }
    }

}
