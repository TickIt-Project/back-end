package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class FieldIsMandatoryNotAcceptedException extends DomainException {
    public FieldIsMandatoryNotAcceptedException() {
        super("Is mandatory needs to be true or false", HttpStatus.NOT_ACCEPTABLE);
    }
}
