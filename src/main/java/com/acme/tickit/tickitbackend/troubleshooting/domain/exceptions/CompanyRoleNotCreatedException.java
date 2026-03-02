package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class CompanyRoleNotCreatedException extends DomainException {
    public CompanyRoleNotCreatedException(String message) {
        super("This company role could not be created: " + message, HttpStatus.BAD_REQUEST);
    }
}
