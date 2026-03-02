package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class CompanyIdNullException extends DomainException {
    public CompanyIdNullException() {
        super("This company id is null", HttpStatus.BAD_REQUEST);
    }
}
