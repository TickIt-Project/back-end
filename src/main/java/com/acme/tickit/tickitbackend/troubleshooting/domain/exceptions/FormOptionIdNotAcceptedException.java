package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class FormOptionIdNotAcceptedException extends DomainException {
    public FormOptionIdNotAcceptedException() {
        super("The form option id is not cannot be null or empty", HttpStatus.NOT_ACCEPTABLE);
    }
}
