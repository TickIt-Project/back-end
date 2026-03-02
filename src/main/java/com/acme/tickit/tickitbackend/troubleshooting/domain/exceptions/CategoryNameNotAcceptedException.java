package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class CategoryNameNotAcceptedException extends DomainException {
    public CategoryNameNotAcceptedException() {
        super("The category name cannot be null or empty", HttpStatus.NOT_ACCEPTABLE);
    }
}
