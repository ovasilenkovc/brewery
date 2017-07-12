package com.brewery.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthentiacationException extends AuthenticationException {
    public JwtAuthentiacationException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtAuthentiacationException(String msg) {
        super(msg);
    }

    public JwtAuthentiacationException(String msg, Object extraInformation) {
        super(msg, extraInformation);
    }
}
