package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class UserIdNotAcceptedException extends DomainException {
    public UserIdNotAcceptedException() {
        super("The user ID cannot be null", HttpStatus.NOT_ACCEPTABLE);
    }
}
