package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class CategoryNameNotAcceptedException extends RuntimeException {
    public CategoryNameNotAcceptedException() {
        super("The category name cannot be null or empty");
    }
}
