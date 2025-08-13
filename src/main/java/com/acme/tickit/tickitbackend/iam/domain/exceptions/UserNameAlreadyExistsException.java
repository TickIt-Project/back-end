package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class UserNameAlreadyExistsException extends RuntimeException {
    public UserNameAlreadyExistsException(String message) {
        super("The username " + message + " already exists");
    }
}
