package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class FormOptionNotCreatedException extends DomainException {
    public FormOptionNotCreatedException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
