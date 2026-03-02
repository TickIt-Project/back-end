package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class CategoryDescriptionNotAcceptedException extends DomainException {
    public CategoryDescriptionNotAcceptedException() {
        super("The category description cannot be null or empty", HttpStatus.NOT_ACCEPTABLE);
    }
}
