package com.acme.tickit.tickitbackend.shared.domain.exceptions;

import org.springframework.http.HttpStatus;

public class CompanyIdNotFoundException extends DomainException {
    public CompanyIdNotFoundException(String message) {
        super("There is no company with id " + message, HttpStatus.NOT_FOUND);
    }
}
