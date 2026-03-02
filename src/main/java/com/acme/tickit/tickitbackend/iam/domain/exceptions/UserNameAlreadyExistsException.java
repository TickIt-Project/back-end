package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class UserNameAlreadyExistsException extends DomainException {
    public UserNameAlreadyExistsException(String message) {
        super("The username " + message + " already exists", HttpStatus.CONFLICT);
    }
}
