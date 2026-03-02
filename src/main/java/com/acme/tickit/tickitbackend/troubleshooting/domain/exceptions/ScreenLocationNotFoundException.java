package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class ScreenLocationNotFoundException extends DomainException {
    public ScreenLocationNotFoundException(String message) {
        super("There is no screen location with the given ID: " + message, HttpStatus.NOT_FOUND);
    }
}
