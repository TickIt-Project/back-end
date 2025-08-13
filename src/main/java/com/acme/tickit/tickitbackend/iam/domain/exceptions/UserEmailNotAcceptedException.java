package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class UserEmailNotAcceptedException extends RuntimeException {
    public UserEmailNotAcceptedException() {
        super("The user email cannot be null or contain an incorrect format");
    }
}
