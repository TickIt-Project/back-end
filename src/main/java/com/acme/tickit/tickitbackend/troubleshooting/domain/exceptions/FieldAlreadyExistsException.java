package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class FieldAlreadyExistsException extends DomainException {
    public FieldAlreadyExistsException(String message) {
        super("The field with the name " + message + " already exists", HttpStatus.CONFLICT);
    }
}
