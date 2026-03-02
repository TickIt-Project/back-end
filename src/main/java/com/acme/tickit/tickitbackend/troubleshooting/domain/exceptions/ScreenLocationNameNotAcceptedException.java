package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class ScreenLocationNameNotAcceptedException extends DomainException {
    public ScreenLocationNameNotAcceptedException() {
        super("The name of the screen location is not accepted", HttpStatus.NOT_ACCEPTABLE);
    }
}
