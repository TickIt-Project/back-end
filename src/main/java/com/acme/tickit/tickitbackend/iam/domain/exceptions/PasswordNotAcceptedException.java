package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class PasswordNotAcceptedException extends RuntimeException {
    public PasswordNotAcceptedException() {
        super("The password should be of at least 8 characters");
    }
}
