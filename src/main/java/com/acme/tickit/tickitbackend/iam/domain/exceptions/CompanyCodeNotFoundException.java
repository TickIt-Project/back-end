package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class CompanyCodeNotFoundException extends RuntimeException {
    public CompanyCodeNotFoundException(String message) {
        super("The company code " + message + " was not found");
    }
}
