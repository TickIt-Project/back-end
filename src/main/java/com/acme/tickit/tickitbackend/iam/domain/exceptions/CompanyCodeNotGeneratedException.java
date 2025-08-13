package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class CompanyCodeNotGeneratedException extends RuntimeException {
    public CompanyCodeNotGeneratedException() {
        super("The company code could not be generated");
    }
}
