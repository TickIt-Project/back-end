package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class EmailNotAcceptedException extends DomainException {
    public EmailNotAcceptedException() {
        super("Email should have the correct format", HttpStatus.NOT_ACCEPTABLE);
    }
}
