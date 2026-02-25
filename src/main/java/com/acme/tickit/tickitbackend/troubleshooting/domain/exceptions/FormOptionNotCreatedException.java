package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class FormOptionNotCreatedException extends RuntimeException {
    public FormOptionNotCreatedException(String message) {
        super(message);
    }
}
