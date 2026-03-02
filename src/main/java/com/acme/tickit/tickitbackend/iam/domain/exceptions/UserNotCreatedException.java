package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class UserNotCreatedException extends DomainException {
    public UserNotCreatedException(String message) {
        super("The user could not be created: " + message, HttpStatus.BAD_REQUEST);
    }
}
