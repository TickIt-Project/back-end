package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class FieldIsMandatoryNotAcceptedException extends RuntimeException {
    public FieldIsMandatoryNotAcceptedException() {
        super("Is mandatory needs to be true or false");
    }
}
