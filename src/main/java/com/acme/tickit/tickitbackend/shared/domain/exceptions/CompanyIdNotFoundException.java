package com.acme.tickit.tickitbackend.shared.domain.exceptions;

public class CompanyIdNotFoundException extends RuntimeException {
    public CompanyIdNotFoundException(String message) {
        super("There is no company with id " + message);
    }
}
