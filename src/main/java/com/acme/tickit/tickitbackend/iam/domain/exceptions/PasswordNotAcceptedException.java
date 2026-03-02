package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class PasswordNotAcceptedException extends DomainException {
    public PasswordNotAcceptedException() {
        super("The password should be of at least 8 characters", HttpStatus.NOT_ACCEPTABLE);
    }
}
