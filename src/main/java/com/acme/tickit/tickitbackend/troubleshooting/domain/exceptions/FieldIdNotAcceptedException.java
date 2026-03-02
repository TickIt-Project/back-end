package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class FieldIdNotAcceptedException extends DomainException {
    public FieldIdNotAcceptedException() {
        super("This field id cannot be null or empty", HttpStatus.NOT_ACCEPTABLE);
    }
}
