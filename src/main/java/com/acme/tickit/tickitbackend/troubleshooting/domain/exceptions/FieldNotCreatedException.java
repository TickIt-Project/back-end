package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class FieldNotCreatedException extends DomainException {
    public FieldNotCreatedException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
