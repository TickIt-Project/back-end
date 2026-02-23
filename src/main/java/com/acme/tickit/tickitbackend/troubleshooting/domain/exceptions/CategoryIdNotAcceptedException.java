package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class CategoryIdNotAcceptedException extends RuntimeException {
    public CategoryIdNotAcceptedException() {
        super("The category ID is not accepted");
    }
}
