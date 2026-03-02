package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class OptionNameNotAcceptedException extends DomainException {
    public OptionNameNotAcceptedException() {
        super("The name for the option cannot be null or empty", HttpStatus.NOT_ACCEPTABLE);
    }
}
