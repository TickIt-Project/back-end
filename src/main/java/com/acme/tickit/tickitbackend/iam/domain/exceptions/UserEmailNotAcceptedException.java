package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class UserEmailNotAcceptedException extends DomainException {
    public UserEmailNotAcceptedException() {
        super("The user email cannot be null or contain an incorrect format", HttpStatus.NOT_ACCEPTABLE);
    }
}
