package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class FieldNotFoundForCategoryException extends DomainException {
    public FieldNotFoundForCategoryException(UUID fieldId) {
        super("Field not found for this category: " + fieldId, HttpStatus.NOT_FOUND);
    }
}
