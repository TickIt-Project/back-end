package com.acme.tickit.tickitbackend.shared.domain.exceptions;

import org.springframework.http.HttpStatus;

public class CompanyIdNotAcceptedException extends DomainException {
    public CompanyIdNotAcceptedException() {
        super("This company ID is null and not accepted", HttpStatus.NOT_ACCEPTABLE);
    }
}
