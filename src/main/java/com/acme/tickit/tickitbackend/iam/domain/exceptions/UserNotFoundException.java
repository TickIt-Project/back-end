package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super("The username " + message + " was not found.");
    }
}
