package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super("The email " + message + " already exists");
    }
}
