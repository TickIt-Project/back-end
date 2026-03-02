package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class CompanyNotCreatedException extends DomainException {
    public CompanyNotCreatedException(String message) {
        super("Company could not be created: " + message, HttpStatus.BAD_REQUEST);
    }
}
