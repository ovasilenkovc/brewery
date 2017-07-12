package com.brewery.exceptions;

public class UserNotFoundException extends RuntimeException{

    /**
     * Constructs a {@code UserNotFoundException} with no detail message.
     */
    public UserNotFoundException() {
        super();
    }

    /**
     * Constructs a {@code UserNotFoundException} with the specified
     * detail message.
     *
     * @param message   the detail message.
     */
    public UserNotFoundException(String message) {
        super(message);
    }

}
