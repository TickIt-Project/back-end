package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class FieldAlreadyExistsException extends RuntimeException {
    public FieldAlreadyExistsException(String message) {
        super("The field with the name " + message + " already exists");
    }
}
