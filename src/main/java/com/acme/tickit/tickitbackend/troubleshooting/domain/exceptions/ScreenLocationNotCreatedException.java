package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class ScreenLocationNotCreatedException extends DomainException {
    public ScreenLocationNotCreatedException(String message) {
        super("This screen location could not be created " + message, HttpStatus.BAD_REQUEST);
    }
}
