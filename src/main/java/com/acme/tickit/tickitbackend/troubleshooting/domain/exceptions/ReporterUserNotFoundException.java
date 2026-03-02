package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class ReporterUserNotFoundException extends DomainException {
    public ReporterUserNotFoundException(String message) {
        super("The reporter user with id " + message + " was not found", HttpStatus.NOT_FOUND);
    }
}
