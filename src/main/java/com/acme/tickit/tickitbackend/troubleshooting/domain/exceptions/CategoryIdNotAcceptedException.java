package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class CategoryIdNotAcceptedException extends DomainException {
    public CategoryIdNotAcceptedException() {
        super("The category ID is not accepted", HttpStatus.NOT_ACCEPTABLE);
    }
}
