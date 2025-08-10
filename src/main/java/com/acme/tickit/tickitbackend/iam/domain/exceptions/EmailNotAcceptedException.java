package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class EmailNotAcceptedException extends RuntimeException {
    public EmailNotAcceptedException() {
        super("Email should have the correct format");
    }
}
