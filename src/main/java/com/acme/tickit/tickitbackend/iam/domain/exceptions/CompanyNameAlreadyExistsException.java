package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class CompanyNameAlreadyExistsException extends RuntimeException {
    public CompanyNameAlreadyExistsException(String message) {
        super("A company named " + message + " already exists");
    }
}
