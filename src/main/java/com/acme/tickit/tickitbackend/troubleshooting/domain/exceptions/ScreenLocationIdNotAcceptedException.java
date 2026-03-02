package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class ScreenLocationIdNotAcceptedException extends DomainException {
    public ScreenLocationIdNotAcceptedException() {
        super("The screen location id is not accepted or does not exists", HttpStatus.NOT_ACCEPTABLE);
    }
}
