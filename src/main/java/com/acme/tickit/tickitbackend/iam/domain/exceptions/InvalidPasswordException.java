package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends DomainException {
    public InvalidPasswordException() {
        super("This password is invalid", HttpStatus.BAD_REQUEST);
    }
}
