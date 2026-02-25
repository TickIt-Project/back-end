package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class FieldTypeNotAcceptedException extends RuntimeException {
    public FieldTypeNotAcceptedException() {
        super("The field type cannot be null or empty");
    }
}
