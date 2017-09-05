package com.brewery.exceptions;

public class CustomRestException extends RuntimeException{

    public CustomRestException() {
    }

    public CustomRestException(String s) {
        super(s);
    }

    public CustomRestException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CustomRestException(Throwable throwable) {
        super(throwable);
    }

}
