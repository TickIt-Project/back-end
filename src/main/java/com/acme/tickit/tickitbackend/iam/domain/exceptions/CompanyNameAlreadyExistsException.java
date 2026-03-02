package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class CompanyNameAlreadyExistsException extends DomainException {
    public CompanyNameAlreadyExistsException(String message) {
        super("A company named " + message + " already exists", HttpStatus.CONFLICT);
    }
}
