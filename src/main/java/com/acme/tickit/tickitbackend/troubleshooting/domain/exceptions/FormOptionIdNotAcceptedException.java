package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class FormOptionIdNotAcceptedException extends RuntimeException {
    public FormOptionIdNotAcceptedException() {
        super("The form option id is not cannot be null or empty");
    }
}
