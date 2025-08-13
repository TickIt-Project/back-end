package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class CompanyIdNullException extends RuntimeException {
    public CompanyIdNullException() {
        super("This company id is null");
    }
}
