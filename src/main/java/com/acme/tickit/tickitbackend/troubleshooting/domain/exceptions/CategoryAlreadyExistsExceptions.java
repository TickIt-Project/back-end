package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class CategoryAlreadyExistsExceptions extends DomainException {
    public CategoryAlreadyExistsExceptions(String message) {
        super("The category with name " + message + " already exists", HttpStatus.CONFLICT);
    }
}
