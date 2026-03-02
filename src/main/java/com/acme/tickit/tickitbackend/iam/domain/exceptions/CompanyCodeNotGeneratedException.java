package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class CompanyCodeNotGeneratedException extends DomainException {
    public CompanyCodeNotGeneratedException() {
        super("The company code could not be generated", HttpStatus.BAD_REQUEST);
    }
}
