package com.acme.tickit.tickitbackend.shared.domain.exceptions;

public class CompanyIdNotAcceptedException extends RuntimeException {
    public CompanyIdNotAcceptedException() {
        super("This company ID is null and not accepted");
    }
}
