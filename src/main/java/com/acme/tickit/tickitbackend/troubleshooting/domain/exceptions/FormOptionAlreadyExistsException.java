package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class FormOptionAlreadyExistsException extends RuntimeException {
    public FormOptionAlreadyExistsException(String message) {
        super("The form option with the name " + message + " already exists");
    }
}
