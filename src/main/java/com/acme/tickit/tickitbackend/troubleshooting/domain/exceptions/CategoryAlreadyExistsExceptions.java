package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class CategoryAlreadyExistsExceptions extends RuntimeException {
    public CategoryAlreadyExistsExceptions(String message) {
        super("The category with name " + message + " already exists");
    }
}
