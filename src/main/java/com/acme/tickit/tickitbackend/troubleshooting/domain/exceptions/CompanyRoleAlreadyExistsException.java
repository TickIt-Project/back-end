package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class CompanyRoleAlreadyExistsException extends DomainException {
    public CompanyRoleAlreadyExistsException(String message) {
        super("The company role " + message + " already exists", HttpStatus.CONFLICT);
    }
}
