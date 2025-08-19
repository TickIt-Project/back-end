package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("This password is invalid");
    }
}
