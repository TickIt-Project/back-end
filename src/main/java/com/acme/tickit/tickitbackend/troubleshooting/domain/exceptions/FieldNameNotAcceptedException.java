package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class FieldNameNotAcceptedException extends DomainException {
    public FieldNameNotAcceptedException() {
        super("The field name cannot be null or empty", HttpStatus.NOT_ACCEPTABLE);
    }
}
