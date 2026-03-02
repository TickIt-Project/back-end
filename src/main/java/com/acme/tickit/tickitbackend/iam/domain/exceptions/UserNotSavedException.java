package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class UserNotSavedException extends DomainException {
    public UserNotSavedException(String message) {
        super("User could not be saved: " + message, HttpStatus.BAD_REQUEST);
    }
}
