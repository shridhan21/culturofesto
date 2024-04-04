package com.fsd.exception;
public class AdminAlreadyExistsException extends RuntimeException {

    public AdminAlreadyExistsException(String message) {
        super(message);
    }
}
