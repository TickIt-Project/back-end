package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class UserIdNullException extends DomainException {
    public UserIdNullException() {
        super("User id cannot be null", HttpStatus.BAD_REQUEST);
    }
}
