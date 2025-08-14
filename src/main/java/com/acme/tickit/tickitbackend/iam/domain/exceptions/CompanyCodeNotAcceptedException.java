package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class CompanyCodeNotAcceptedException extends RuntimeException {
    public CompanyCodeNotAcceptedException() {
        super("The code is not accepted or null, it must be 7 characters long");
    }
}
