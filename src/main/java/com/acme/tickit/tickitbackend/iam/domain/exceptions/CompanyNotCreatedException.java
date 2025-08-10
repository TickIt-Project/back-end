package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class CompanyNotCreatedException extends RuntimeException {
    public CompanyNotCreatedException(String message) {
        super("Company could not be created: " + message);
    }
}
