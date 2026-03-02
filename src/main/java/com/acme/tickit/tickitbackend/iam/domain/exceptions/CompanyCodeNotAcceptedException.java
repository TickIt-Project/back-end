package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class CompanyCodeNotAcceptedException extends DomainException {
    public CompanyCodeNotAcceptedException() {
        super("The code is not accepted or null, it must be 7 characters long", HttpStatus.BAD_REQUEST);
    }
}
