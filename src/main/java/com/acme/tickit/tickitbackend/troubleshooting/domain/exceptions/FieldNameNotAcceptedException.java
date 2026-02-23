package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class FieldNameNotAcceptedException extends RuntimeException {
    public FieldNameNotAcceptedException() {
        super("The field name cannot be null or empty");
    }
}
