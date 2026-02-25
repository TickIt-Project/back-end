package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class FieldNotCreatedException extends RuntimeException {
    public FieldNotCreatedException(String message) {
        super(message);
    }
}
