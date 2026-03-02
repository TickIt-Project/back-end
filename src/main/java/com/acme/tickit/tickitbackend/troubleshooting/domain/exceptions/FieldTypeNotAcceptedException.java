package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class FieldTypeNotAcceptedException extends DomainException {
    public FieldTypeNotAcceptedException() {
        super("The field type cannot be null or empty", HttpStatus.NOT_ACCEPTABLE);
    }
}
