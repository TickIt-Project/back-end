package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class UserNotCreatedException extends RuntimeException {
    public UserNotCreatedException(String message) {
        super("The user could not be created: " + message);
    }
}
