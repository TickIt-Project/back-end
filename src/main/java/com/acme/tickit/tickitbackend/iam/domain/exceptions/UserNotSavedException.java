package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class UserNotSavedException extends RuntimeException {
    public UserNotSavedException(String message) {
        super("User could not be saved: " + message);
    }
}
