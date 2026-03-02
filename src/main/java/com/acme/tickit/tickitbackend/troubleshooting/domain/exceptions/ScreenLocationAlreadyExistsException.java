package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class ScreenLocationAlreadyExistsException extends DomainException {
    public ScreenLocationAlreadyExistsException(String message) {
        super("A screen with the name or url " + message + " already exists", HttpStatus.CONFLICT);
    }
}
