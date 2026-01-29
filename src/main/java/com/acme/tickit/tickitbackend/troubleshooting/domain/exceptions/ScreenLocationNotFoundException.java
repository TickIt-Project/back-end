package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class ScreenLocationNotFoundException extends RuntimeException {
    public ScreenLocationNotFoundException(String message) {
        super("There is no screen location with the given ID: " + message);
    }
}
