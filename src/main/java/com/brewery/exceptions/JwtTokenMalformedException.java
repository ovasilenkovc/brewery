package com.brewery.exceptions;

public class JwtTokenMalformedException extends RuntimeException{

    public JwtTokenMalformedException() {
    }

    public JwtTokenMalformedException(String message) {
        super(message);
    }

    public JwtTokenMalformedException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtTokenMalformedException(Throwable cause) {
        super(cause);
    }

    public JwtTokenMalformedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
