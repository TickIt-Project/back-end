package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends DomainException {
    public EmailAlreadyExistsException(String message) {
        super("The email " + message + " already exists", HttpStatus.CONFLICT);
    }
}
