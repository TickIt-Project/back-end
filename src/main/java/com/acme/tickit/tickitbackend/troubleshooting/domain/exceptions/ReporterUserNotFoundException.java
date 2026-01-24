package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class ReporterUserNotFoundException extends RuntimeException {
    public ReporterUserNotFoundException(String message) {
        super("The reporter user with id " + message + " was not found");
    }
}
