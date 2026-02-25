package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class CategoryDescriptionNotAcceptedException extends RuntimeException {
    public CategoryDescriptionNotAcceptedException() {
        super("The category description cannot be null or empty");
    }
}
