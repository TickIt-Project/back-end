package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class UserNameNotAcceptedException extends DomainException {
    public UserNameNotAcceptedException() {
        super("The username is not accepted, it cannot be null or with less than 2 characters", HttpStatus.NOT_ACCEPTABLE);
    }
}
