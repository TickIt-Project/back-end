package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class CompanyNameNotAcceptedException extends RuntimeException {
    public CompanyNameNotAcceptedException() {
        super("Company name null not accepted");
    }
}
