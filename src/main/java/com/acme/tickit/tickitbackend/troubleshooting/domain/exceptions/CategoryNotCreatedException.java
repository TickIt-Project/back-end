package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class CategoryNotCreatedException extends RuntimeException {
    public CategoryNotCreatedException(String message) {
        super(message);
    }
}
