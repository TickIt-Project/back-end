package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class NameNotAcceptedException extends RuntimeException {
    public NameNotAcceptedException() {
        super("Name cannot be null or empty");
    }
}
