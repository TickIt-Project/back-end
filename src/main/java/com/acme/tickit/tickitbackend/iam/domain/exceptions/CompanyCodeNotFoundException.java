package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class CompanyCodeNotFoundException extends DomainException {
    public CompanyCodeNotFoundException(String message) {
        super("The company code " + message + " was not found", HttpStatus.NOT_FOUND);
    }
}
