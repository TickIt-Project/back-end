package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends DomainException {
    public UserNotFoundException(String message) {
        super("The username " + message + " was not found.", HttpStatus.NOT_FOUND);
    }
}
