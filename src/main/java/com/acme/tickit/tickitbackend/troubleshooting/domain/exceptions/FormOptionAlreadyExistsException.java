package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class FormOptionAlreadyExistsException extends DomainException {
    public FormOptionAlreadyExistsException(String message) {
        super("The form option with the name " + message + " already exists", HttpStatus.CONFLICT);
    }
}
