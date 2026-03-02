package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class KeywordNotAcceptedException extends DomainException {
    public KeywordNotAcceptedException(String message) {
        super("The word " + message + " is not accepted", HttpStatus.NOT_ACCEPTABLE);
    }
}
