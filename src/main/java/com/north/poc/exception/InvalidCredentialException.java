package com.north.poc.exception;

public class InvalidCredentialException extends RuntimeException{
    public InvalidCredentialException() {
    }

    public InvalidCredentialException(String message) {
        super(message);
    }
}
