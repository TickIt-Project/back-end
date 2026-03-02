package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class CompanyNameNotAcceptedException extends DomainException {
    public CompanyNameNotAcceptedException() {
        super("Company name null not accepted", HttpStatus.BAD_REQUEST);
    }
}
