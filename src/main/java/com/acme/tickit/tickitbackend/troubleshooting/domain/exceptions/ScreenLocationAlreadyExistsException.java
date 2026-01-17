package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class ScreenLocationAlreadyExistsException extends RuntimeException {
    public ScreenLocationAlreadyExistsException(String message) {
        super("A screen with the name or url " + message + " already exists");
    }
}
