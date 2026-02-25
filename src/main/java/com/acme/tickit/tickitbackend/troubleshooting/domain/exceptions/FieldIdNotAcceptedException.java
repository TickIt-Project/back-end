package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class FieldIdNotAcceptedException extends RuntimeException {
    public FieldIdNotAcceptedException() {
        super("This field id cannot be null or empty");
    }
}
