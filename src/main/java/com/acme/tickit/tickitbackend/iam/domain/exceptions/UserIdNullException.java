package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class UserIdNullException extends RuntimeException {
    public UserIdNullException() {
        super("User id cannot be null");
    }
}
