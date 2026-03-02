package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class NameNotAcceptedException extends DomainException {
    public NameNotAcceptedException() {
        super("Name cannot be null or empty", HttpStatus.NOT_ACCEPTABLE);
    }
}
