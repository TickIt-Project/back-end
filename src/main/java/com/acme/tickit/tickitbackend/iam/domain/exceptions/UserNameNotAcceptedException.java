package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class UserNameNotAcceptedException extends RuntimeException {
    public UserNameNotAcceptedException() {
        super("The username is not accepted, it cannot be null or with less than 2 characters");
    }
}
